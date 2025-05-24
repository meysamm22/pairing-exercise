package io.billie.countries.presentation.viewmodel

import com.fasterxml.jackson.annotation.JsonProperty

data class CountryViewModel(
    val id: String,
    val name: String,
    @JsonProperty("country_code") val code: String
)