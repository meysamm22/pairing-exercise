package io.billie.orders.infrastructure.repository

import io.billie.orders.domain.OrderFactory
import io.billie.orders.domain.model.Order
import io.billie.orders.domain.repositoryinterface.OrderRepositoryInterface
import io.billie.orders.infrastructure.dto.OrderDatabaseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.sql.PreparedStatement
import java.util.UUID

@Repository
internal class OrderRepository(): OrderRepositoryInterface {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var orderFactory: OrderFactory

    override fun findById(id: UUID): Order? {
        val sql = """
            SELECT 
                o.id as order_id, 
                o.total_amount as order_amount,
                o.merchant_id as merchant_id,
                o.buyer_id as buyer_id,
                o.total_amount as order_amount,
                s.id as shipment_id, 
                s.amount as shipment_amount
            FROM orders_schema.orders o
            LEFT JOIN orders_schema.orders_shipments s ON o.id = s.order_id
            WHERE o.id = ?
        """.trimIndent()

        val rows = jdbcTemplate.queryForList(sql, id)

        if (rows.isEmpty()) {
            return null
        }

        val shipments: MutableMap<UUID, BigDecimal> = mutableMapOf()
        var orderDatabaseDto: OrderDatabaseDto? = null

        for (row in rows) {
            if (orderDatabaseDto == null) {
                orderDatabaseDto = OrderDatabaseDto(
                    row["order_id"] as UUID,
                    row["order_amount"] as BigDecimal,
                    mutableMapOf(),
                    row["merchant_id"] as UUID,
                    row["buyer_id"] as UUID,
                )
            }

            val shipmentId = row["shipment_id"] as UUID?
            if (shipmentId != null) {
                shipments.set(shipmentId, row["shipment_amount"] as BigDecimal)
            }
        }

        return orderFactory.createFromDto(
            orderDatabaseDto!!.copy(shipments = shipments)
        )
    }

    @Transactional
    override fun save(order: Order) {

        jdbcTemplate.update(
            "UPDATE orders_schema.orders SET total_amount = ? WHERE id = ?",
            order.totalAmount.amount, order.id
        )

        val newShipments = order.shipments.filter { it.id == null }
        if (newShipments.isEmpty()) {
            return
        }

        val insertSql = "INSERT INTO orders_schema.orders_shipments (order_id, amount) VALUES (?, ?)"
        jdbcTemplate.batchUpdate(insertSql, object : BatchPreparedStatementSetter {
            override fun setValues(ps: PreparedStatement, i: Int) {
                val shipment = newShipments[i]
                ps.setObject(1, order.id)
                ps.setBigDecimal(2, shipment.amount.amount)
            }

            override fun getBatchSize(): Int = newShipments.size
        })
    }
}