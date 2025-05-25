package io.billie.orders.infrastructure

import java.math.BigDecimal
import java.util.UUID

data class OrderDatabaseDto(
    val id: UUID,
    val totalAmount: BigDecimal,
    val shipments: MutableMap<UUID, BigDecimal>,
    val merchantId: UUID,
    val buyerId: UUID
)
