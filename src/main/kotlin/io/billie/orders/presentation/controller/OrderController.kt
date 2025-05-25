package io.billie.orders.presentation.controller

import io.billie.orders.application.dto.AddShipmentRequestDto
import io.billie.orders.application.usecase.AddShipmentUseCase
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("orders")
class OrderController(
    val addShipmentUseCase: AddShipmentUseCase
) {
    @PostMapping("/{orderId}/shipments")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Accepted the new shipment"
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
    )
    fun addShipmentToOrder(
        @PathVariable("orderId") orderId: String,
        @RequestParam
        @NotNull(message = "Shipment amount is required")
        @DecimalMin(value = "0.01", message = "Shipment amount must be positive")
        shipmentAmount: BigDecimal
    ): ResponseEntity<Any>{
        return try {
            val uuid = UUID.fromString(orderId)
            addShipmentUseCase.add(AddShipmentRequestDto(uuid, shipmentAmount))
            ResponseEntity.ok().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to "Invalid UUID format"))
        } catch (e: RuntimeException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}