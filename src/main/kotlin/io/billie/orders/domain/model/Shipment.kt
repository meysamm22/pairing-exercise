package io.billie.orders.domain.model

import io.billie.orders.domain.exception.InvalidMoneyValueException
import java.math.BigDecimal
import java.util.UUID

internal class Shipment private constructor(
    private val _id: UUID?,
    private val _amount: Money
) {
    val id: UUID? get() = _id
    val amount: Money get() = _amount

    internal companion object {
        fun create(
            id: UUID?,
            amount: Money
        ): Shipment {
            if (amount.amount < BigDecimal.valueOf(0)) {
                throw InvalidMoneyValueException("Shipment amount must be positive")
            }
            return Shipment(id, amount)
        }
    }
}
