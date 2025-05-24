package io.billie.organisations.presentation.controller

import io.billie.organisations.presentation.dto.OrganisationRequestDto
import io.billie.organisations.application.usecase.CreateOrganisationUseCase
import io.billie.organisations.application.usecase.ListOrganisationUseCase
import io.billie.organisations.presentation.viewmodel.CreatedOrganisationViewModel
import io.billie.organisations.presentation.viewmodel.OrganisationViewModel
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
@RequestMapping("organisations")
class OrganisationController(
    val createOrganisationUseCase: CreateOrganisationUseCase,
    val listOrganisationUseCase: ListOrganisationUseCase
) {

    @GetMapping
    fun index(): List<OrganisationViewModel> = listOrganisationUseCase.findOrganisations()

    @PostMapping
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Accepted the new organisation",
                content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = CreatedOrganisationViewModel::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
    )
    fun create(@Valid @RequestBody organisation: OrganisationRequestDto): ResponseEntity<CreatedOrganisationViewModel> {
        try {
            return ResponseEntity.ok(createOrganisationUseCase.createOrganisation(organisation))
        } catch (e: RuntimeException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

}