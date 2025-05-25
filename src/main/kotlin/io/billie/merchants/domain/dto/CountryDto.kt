package io.billie.merchants.domain.dto

import java.util.UUID

data class CountryDto(
    val id: UUID,
    val name: String,
    val code: String
)