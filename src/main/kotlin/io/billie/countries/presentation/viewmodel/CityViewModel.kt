package io.billie.countries.presentation.viewmodel

import com.fasterxml.jackson.annotation.JsonProperty

data class CityViewModel(
    val id: String,
    val name: String,
    @JsonProperty("country_code")
    val countryCode: String
)