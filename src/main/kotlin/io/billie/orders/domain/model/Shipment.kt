package io.billie.orders.domain.model

import java.math.BigDecimal
import java.util.UUID

internal class Shipment private constructor(
    private val _id: UUID,
    private val _amount: Money
) {
    val uuid: UUID get() = _id
    val amount: Money get() = _amount

    internal companion object {
        fun create(
            id: UUID,
            amount: Money
        ): Shipment {
            return Shipment(id, amount)
        }
    }
}
