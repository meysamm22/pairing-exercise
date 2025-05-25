package io.billie.orders.application.dto

import java.math.BigDecimal
import java.util.UUID

data class AddShipmentRequestDto(
    val orderId: UUID,
    val shipmentsAmount: BigDecimal,
)
