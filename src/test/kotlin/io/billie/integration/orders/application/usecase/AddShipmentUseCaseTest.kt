package io.billie.integration.orders.application.usecase

import io.billie.merchants.domain.dto.MerchantExposeDto
import io.billie.merchants.infrastructure.MerchantQueryService
import io.billie.orders.application.dto.AddShipmentRequestDto
import io.billie.orders.application.usecase.AddShipmentUseCase
import io.billie.orders.domain.exception.ExceededOrderAmountException
import io.billie.orders.domain.exception.InvalidMoneyValueException
import io.billie.orders.domain.exception.OrderNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@SpringBootTest(webEnvironment = DEFINED_PORT)
@Transactional
@ActiveProfiles("test")
class AddShipmentUseCaseTest {
    private val merchantId: UUID = UUID.randomUUID()
    private val buyerId: UUID = UUID.randomUUID()

    @Autowired
    private lateinit var template: JdbcTemplate

    @Autowired
    private lateinit var addShipmentUseCase: AddShipmentUseCase
    @MockBean
    lateinit var merchantQueryService: MerchantQueryService

    @Test
    fun addValidShipmentToValidOrderTest() {
        `when`(merchantQueryService.findById(merchantId))
            .thenReturn(MerchantExposeDto(merchantId, "Fake merchant name"))
        val orderId = addSampleOrderToDatabase()
        val addShipmentRequestDto = AddShipmentRequestDto(orderId, BigDecimal.valueOf(10))
        addShipmentUseCase.add(addShipmentRequestDto)

        Assertions.assertTrue(shipmentExists(orderId, addShipmentRequestDto.shipmentsAmount))

    }

    @Test
    fun addValidShipmentToOrderWhichIsNotExistsTest() {
        `when`(merchantQueryService.findById(merchantId))
            .thenReturn(MerchantExposeDto(merchantId, "Fake merchant name"))
        val orderId = UUID.randomUUID()
        val addShipmentRequestDto = AddShipmentRequestDto(orderId, BigDecimal.valueOf(10))
        val ex = assertThrows(OrderNotFoundException::class.java) {
            addShipmentUseCase.add(addShipmentRequestDto)
        }
        assertEquals("Order not found", ex.message)

        Assertions.assertTrue(shipmentExists(orderId, addShipmentRequestDto.shipmentsAmount))

    }

    @Test
    fun addInValidShipmentToOrderTest() {
        `when`(merchantQueryService.findById(merchantId))
            .thenReturn(MerchantExposeDto(merchantId, "Fake merchant name"))
        val orderId = addSampleOrderToDatabase()
        val addShipmentRequestDto = AddShipmentRequestDto(orderId, BigDecimal.valueOf(-20))
        val ex = assertThrows(InvalidMoneyValueException::class.java) {
            addShipmentUseCase.add(addShipmentRequestDto)
        }
        assertEquals("Shipment amount must be positive", ex.message)

        Assertions.assertTrue(shipmentExists(orderId, addShipmentRequestDto.shipmentsAmount))

    }

    @Test
    fun addValidShipmentToOrderWhichExceededAmountTest() {
        `when`(merchantQueryService.findById(merchantId))
            .thenReturn(MerchantExposeDto(merchantId, "Fake merchant name"))
        val orderId = addSampleOrderToDatabase()
        addSampleShipmentToOrderToDatabase(orderId)
        val addShipmentRequestDto = AddShipmentRequestDto(orderId, BigDecimal.valueOf(40.99))
        val ex = assertThrows(ExceededOrderAmountException::class.java) {
            addShipmentUseCase.add(addShipmentRequestDto)
        }
        assertEquals("Shipment amounts must not exceed the total amount or the order", ex.message)

        Assertions.assertTrue(shipmentExists(orderId, addShipmentRequestDto.shipmentsAmount))

    }

    private fun addSampleOrderToDatabase() : UUID{
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        template.update(
            { connection ->
                val ps = connection.prepareStatement(
                    "insert into orders_schema.orders " +
                            "(" +
                            "total_amount, " +
                            "merchant_id, " +
                            "buyer_id" +
                            ") values(?,?,?)",
                    arrayOf("id")
                )
                ps.setBigDecimal(1, BigDecimal.valueOf(32.99))
                ps.setString(2, merchantId.toString())
                ps.setString(3, buyerId.toString())
                ps
            },
            keyHolder
        )
        return keyHolder.getKeyAs(UUID::class.java)!!
    }

    private fun addSampleShipmentToOrderToDatabase(orderId: UUID) : UUID{
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        template.update(
            { connection ->
                val ps = connection.prepareStatement(
                    "insert into orders_schema.orders_shipments " +
                            "(" +
                            "order_id, " +
                            "amount " +
                            ") values(?,?)",
                    arrayOf("id")
                )
                ps.setString(1, orderId.toString())
                ps.setBigDecimal(2, BigDecimal.valueOf(10.99))
                ps
            },
            keyHolder
        )
        return keyHolder.getKeyAs(UUID::class.java)!!
    }

    private fun shipmentExists(orderId: UUID, shipmentAmount: BigDecimal): Boolean {
        val sql = """
        SELECT EXISTS (
            SELECT 1 FROM orders_schema.orders_shipments 
            WHERE order_id = ? AND amount = ?
        )
        """.trimIndent()

        return template.queryForObject(sql, Boolean::class.java, orderId.toString(), shipmentAmount) ?: false
    }


}