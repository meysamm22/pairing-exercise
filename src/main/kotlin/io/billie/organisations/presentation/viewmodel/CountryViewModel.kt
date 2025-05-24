package io.billie.organisations.presentation.viewmodel

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class CountryViewModel(
    val id: UUID?,
    val name: String,
    @JsonProperty("country_code") val code: String
)
