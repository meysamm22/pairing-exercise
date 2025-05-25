package io.billie.merchants.domain

import io.billie.merchants.domain.dto.CountryDto

interface CountryProviderInterface {
    fun findAll(): Map<String, CountryDto>
}