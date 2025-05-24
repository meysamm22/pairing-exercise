package io.billie.countries.application

import io.billie.countries.application.dto.CountryDto

interface CountryQueryServiceInterface {
    fun findAll(): List<CountryDto>
}