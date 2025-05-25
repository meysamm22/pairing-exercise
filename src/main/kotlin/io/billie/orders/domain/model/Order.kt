package io.billie.orders.domain.model

import io.billie.orders.domain.exception.ExceededOrderAmountException
import io.billie.orders.domain.exception.InvalidMoneyValueException
import java.math.BigDecimal
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
        val totalShipped = shipments.sumOf { it.amount }
        val newTotal = totalShipped + shipment.amount
        if (newTotal >= totalAmount.amount) {
            throw ExceededOrderAmountException("Shipment amounts must not exceed the total amount or the order")
        }
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

            if (totalAmount.amount < BigDecimal.valueOf(0)) {
                throw InvalidMoneyValueException("Total amount must be positive")
            }

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