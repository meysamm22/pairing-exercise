package io.billie.orders.domain.model

import java.util.UUID

internal class Order private constructor(
    private val _id: UUID,
    private val _totalAmount: Money,
    private val _merchant: MerchantInfo,
    private val _buyer: BuyerInfo,
){
    val id: UUID get() = _id
    val totalAmount: Money get() = _totalAmount
    val shipments: MutableList<Shipment> = mutableListOf()
    val merchant: MerchantInfo get() = _merchant
    val buyer: BuyerInfo get() = _buyer

    internal fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    internal companion object{
        fun create(
            id: UUID,
            totalAmount: Money,
            shipments: MutableList<Shipment>,
            merchant: MerchantInfo,
            buyer: BuyerInfo,
        ) : Order {
            var order = Order(
                id,
                totalAmount,
                merchant,
                buyer
            )
            shipments.forEach { shipment -> order.addShipment(shipment) }

            return order
        }
    }
}