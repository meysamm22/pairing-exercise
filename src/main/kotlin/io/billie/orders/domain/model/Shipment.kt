package io.billie.orders.domain.model

import java.math.BigDecimal
import java.util.UUID

internal class Shipment private constructor(
    private val _id: UUID,
    private val _amount: BigDecimal
) {
    val uuid: UUID get() = _id
    val amount: BigDecimal get() = _amount

    internal companion object {
        fun create(
            id: UUID,
            amount: BigDecimal
        ): Shipment {
            return Shipment(id, amount)
        }
    }
}
