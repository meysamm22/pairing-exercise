package io.billie.unit.orders.domain

import io.billie.merchants.domain.dto.MerchantExposeDto
import io.billie.orders.domain.OrderFactory
import io.billie.orders.domain.exception.ExceededOrderAmountException
import io.billie.orders.domain.exception.InvalidMoneyValueException
import io.billie.orders.domain.exception.MerchantNotFoundException
import io.billie.orders.domain.model.Order
import io.billie.orders.infrastructure.MerchantProviderAdapter
import io.billie.orders.infrastructure.dto.OrderDatabaseDto
import org.junit.jupiter.api.Test
import io.billie.unit.orders.fixtures.OrderFixture.getValidOrderDatabaseDto
import io.billie.unit.orders.fixtures.OrderFixture.getInvalidOrderWithMinusAmountDatabaseDto
import io.billie.unit.orders.fixtures.OrderFixture.getInvalidOrderWithExceededAmountOfShipmentsDatabaseDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.math.BigDecimal

class OrderTest {

    private lateinit var provider: MerchantProviderAdapter
    private lateinit var factory: OrderFactory

    @BeforeEach
    fun setup() {
        provider = mock(MerchantProviderAdapter::class.java)
        factory = OrderFactory(provider)
    }

    @Test
    fun validOrderCreationTest() {
        val dto: OrderDatabaseDto = getValidOrderDatabaseDto()
        `when` (provider.findById(dto.merchantId)).thenReturn(MerchantExposeDto(dto.merchantId, "Merchant name"))
        val order: Order = factory.createFromDto(dto)

        Assertions.assertNotNull(order)
        Assertions.assertEquals(order.id, dto.id)
        Assertions.assertEquals(order.merchant.id, dto.merchantId)
        Assertions.assertEquals(order.buyer.id, dto.buyerId)
        Assertions.assertEquals(order.id, dto.id)
        Assertions.assertEquals(order.totalAmount.amount, dto.totalAmount)
        Assertions.assertEquals(order.shipments.size, dto.shipments.size)
        Assertions.assertTrue(order.totalAmount.amount > BigDecimal.valueOf(0))
        Assertions.assertEquals(
            order.shipments.sumOf {shipment -> shipment.amount.amount},
            dto.shipments.values.sumOf { it }
            )
    }

    @Test
    fun invalidWithMinusTotalAmountOrderCreationTest() {
        val dto: OrderDatabaseDto = getInvalidOrderWithMinusAmountDatabaseDto()
        `when` (provider.findById(dto.merchantId)).thenReturn(MerchantExposeDto(dto.merchantId, "Merchant name"))

        val ex = assertThrows(InvalidMoneyValueException::class.java) {
            factory.createFromDto(dto)
        }
        assertEquals("Total amount must be positive", ex.message)
    }

    @Test
    fun invalidWithExceededTotalAmountOrderCreationTest() {
        val dto: OrderDatabaseDto = getInvalidOrderWithExceededAmountOfShipmentsDatabaseDto()
        `when` (provider.findById(dto.merchantId)).thenReturn(MerchantExposeDto(dto.merchantId, "Merchant name"))

        val ex = assertThrows(ExceededOrderAmountException::class.java) {
            factory.createFromDto(dto)
        }
        assertEquals("Shipment amounts must not exceed the total amount or the order", ex.message)
    }

    @Test
    fun invalidWithBuyerOfOrderTest() {
        val dto: OrderDatabaseDto = getValidOrderDatabaseDto()
        `when` (provider.findById(dto.merchantId)).thenReturn(null)
        val ex = assertThrows(MerchantNotFoundException::class.java) {
             factory.createFromDto(dto)
        }
        assertEquals("Merchant not found", ex.message)

    }

}