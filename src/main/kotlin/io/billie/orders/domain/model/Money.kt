package io.billie.orders.domain.model

import java.math.BigDecimal

internal class Money private constructor(
    private val _amount: BigDecimal
) {
    val amount: BigDecimal get() = _amount

    internal companion object {
        fun create(
            amount: BigDecimal
        ): Money {
            return Money(amount)
        }
    }
}
