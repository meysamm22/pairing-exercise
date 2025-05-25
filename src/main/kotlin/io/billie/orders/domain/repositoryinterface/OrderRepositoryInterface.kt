package io.billie.orders.domain.repositoryinterface

import io.billie.orders.domain.model.Order
import java.util.UUID

internal interface OrderRepositoryInterface {
    fun findById(id: UUID): Order?
    fun save(order: Order)
}