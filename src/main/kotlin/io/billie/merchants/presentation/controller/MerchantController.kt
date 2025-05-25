package io.billie.merchants.presentation.controller

import io.billie.merchants.application.usecase.CreateMerchantUseCase
import io.billie.merchants.application.usecase.ListMerchantUseCase
import io.billie.merchants.presentation.request.MerchantRequest
import io.billie.merchants.presentation.viewmodel.CreatedMerchantViewModel
import io.billie.merchants.presentation.viewmodel.MerchantViewModel
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("merchants")
class MerchantController(
    val createMerchantUseCase: CreateMerchantUseCase,
    val listMerchantUseCase: ListMerchantUseCase
) {

    @GetMapping
        fun index(): List<MerchantViewModel> = listMerchantUseCase.findMerchants()

    @PostMapping
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Accepted the new merchant",
                content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = CreatedMerchantViewModel::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
    )
    fun create(@Valid @RequestBody merchantRequest: MerchantRequest): ResponseEntity<CreatedMerchantViewModel> {
        try {
            return ResponseEntity.ok(createMerchantUseCase.createMerchant(
                    merchantRequest.toMerchantRequestDto()
                )
            )
        } catch (e: RuntimeException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

}