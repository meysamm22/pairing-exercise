package io.billie.orders.domain

import io.billie.orders.domain.model.BuyerInfo
import io.billie.orders.domain.model.MerchantInfo
import io.billie.orders.domain.model.Money
import io.billie.orders.domain.model.Order
import io.billie.orders.domain.model.Shipment
import io.billie.orders.infrastructure.OrderDatabaseDto
import org.springframework.stereotype.Service

@Service
internal class OrderFactory {
    fun createFromDto(orderDatabaseDto: OrderDatabaseDto): Order {
        return Order.create(
            orderDatabaseDto.id,
            Money.create(orderDatabaseDto.totalAmount),
            orderDatabaseDto.shipments.map {
                shipment -> Shipment.create(shipment.key, shipment.value)
            }.toMutableList(),
            // TODO:: Should be replaced with real one
            MerchantInfo.create(orderDatabaseDto.merchantId, "test"),
            BuyerInfo.create(orderDatabaseDto.buyerId, "test")
        )
    }
}