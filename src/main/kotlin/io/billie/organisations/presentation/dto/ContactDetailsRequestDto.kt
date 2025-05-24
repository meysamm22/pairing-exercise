package io.billie.organisations.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ContactDetailsRequestDto(
    @JsonProperty("phone_number") val phoneNumber: String?,
    val fax: String?,
    val email: String?
)