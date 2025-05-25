package io.billie.unit.orders.fixtures

import io.billie.orders.infrastructure.dto.OrderDatabaseDto
import java.math.BigDecimal
import java.util.UUID

object OrderFixture {
    fun getValidOrderDatabaseDto(): OrderDatabaseDto {
        val shipmentMap: HashMap<UUID, BigDecimal> = hashMapOf(
            UUID.randomUUID() to BigDecimal.valueOf(10.99),
            UUID.randomUUID() to BigDecimal.valueOf(20.99),
        )

        return OrderDatabaseDto(
            UUID.randomUUID(),
            BigDecimal.valueOf(32.99),
            shipmentMap,
            UUID.randomUUID(),
            UUID.randomUUID()
        )
    }

    fun getInvalidOrderWithMinusAmountDatabaseDto(): OrderDatabaseDto {
        val shipmentMap: HashMap<UUID, BigDecimal> = hashMapOf(
            UUID.randomUUID() to BigDecimal.valueOf(10.99),
            UUID.randomUUID() to BigDecimal.valueOf(2.99),
        )

        return OrderDatabaseDto(
            UUID.randomUUID(),
            BigDecimal.valueOf(-22.21),
            shipmentMap,
            UUID.randomUUID(),
            UUID.randomUUID()
        )
    }

    fun getInvalidOrderWithExceededAmountOfShipmentsDatabaseDto(): OrderDatabaseDto {
        val shipmentMap: HashMap<UUID, BigDecimal> = hashMapOf(
            UUID.randomUUID() to BigDecimal.valueOf(10.99),
            UUID.randomUUID() to BigDecimal.valueOf(20.99),
        )

        return OrderDatabaseDto(
            UUID.randomUUID(),
            BigDecimal.valueOf(22.21),
            shipmentMap,
            UUID.randomUUID(),
            UUID.randomUUID()
        )
    }
}