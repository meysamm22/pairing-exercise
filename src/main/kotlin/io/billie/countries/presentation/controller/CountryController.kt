package io.billie.countries.presentation.controller

import io.billie.countries.application.usecase.FindCitiesUseCase
import io.billie.countries.application.usecase.FindCountriesUseCase
import io.billie.countries.presentation.viewmodel.CityViewModel
import io.billie.countries.presentation.viewmodel.CountryViewModel
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("countries")
class CountryController(
    val findCountriesUseCase: FindCountriesUseCase,
    val findCitiesUseCase: FindCitiesUseCase
) {

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "All countries",
                content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = CountryViewModel::class)))
                    ))]
            )]
    )
    @GetMapping
    fun index(): List<CountryViewModel> = findCountriesUseCase.findCountries()

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Found cities for country",
                content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = CityViewModel::class)))
                    ))]
            ),
            ApiResponse(responseCode = "404", description = "No cities found for country code", content = [Content()])]
    )
    @GetMapping("/{countryCode}/cities")
    fun cities(@PathVariable("countryCode") countryCode: String): List<CityViewModel> {
        val cities = findCitiesUseCase.findByCountryCode(countryCode.uppercase())
        if (cities.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No cities found for $countryCode"
            )
        }
        return cities
    }
}