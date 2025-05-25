package io.billie.orders.application.usecase

import io.billie.orders.application.dto.AddShipmentRequestDto
import io.billie.orders.application.exception.CouldNotSaveOrderException
import io.billie.orders.domain.exception.OrderNotFoundException
import io.billie.orders.domain.repositoryinterface.OrderRepositoryInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddShipmentUseCase {
    @Autowired
    private lateinit var orderRepository: OrderRepositoryInterface

    fun add(addShipmentRequestDto: AddShipmentRequestDto) {
        val order = orderRepository.findById(addShipmentRequestDto.orderId)

        if (order == null) {
            throw OrderNotFoundException("Order not found")
        }

        order.addShipment(addShipmentRequestDto.shipmentsAmount)
        try {
            orderRepository.save(order)
        } catch (e: RuntimeException) {
            // TODO:: Add some logs here
            throw CouldNotSaveOrderException(e.message.orEmpty());
        }
    }
}