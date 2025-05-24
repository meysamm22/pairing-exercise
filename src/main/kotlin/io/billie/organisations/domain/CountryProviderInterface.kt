package io.billie.organisations.domain

import io.billie.organisations.domain.dto.CountryDto

interface CountryProviderInterface {
    fun findAll(): Map<String, CountryDto>
}