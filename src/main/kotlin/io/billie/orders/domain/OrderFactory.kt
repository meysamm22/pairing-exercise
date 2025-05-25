package io.billie.orders.domain

import io.billie.orders.domain.exception.MerchantNotFoundException
import io.billie.orders.domain.model.BuyerInfo
import io.billie.orders.domain.model.MerchantInfo
import io.billie.orders.domain.model.Money
import io.billie.orders.domain.model.Order
import io.billie.orders.domain.model.Shipment
import io.billie.orders.infrastructure.MerchantProviderAdapter
import io.billie.orders.infrastructure.OrderDatabaseDto
import org.springframework.stereotype.Service

@Service
internal class OrderFactory(private val merchantProviderAdapter: MerchantProviderAdapter) {

    fun createFromDto(orderDatabaseDto: OrderDatabaseDto): Order {

        val merchantInfo = merchantProviderAdapter.findById(orderDatabaseDto.merchantId)
        if (merchantInfo == null) {
            throw MerchantNotFoundException("Merchant not found")
        }

        return Order.create(
            orderDatabaseDto.id,
            Money.create(orderDatabaseDto.totalAmount),
            orderDatabaseDto.shipments.map {
                shipment -> Shipment.create(shipment.key, shipment.value)
            }.toMutableList(),
            MerchantInfo.create(
                merchantInfo.id, merchantInfo.name
            ),
            // TODO:: Should be replaced with real one when the buyer context implemented
            BuyerInfo.create(orderDatabaseDto.buyerId, "test")
        )
    }
}