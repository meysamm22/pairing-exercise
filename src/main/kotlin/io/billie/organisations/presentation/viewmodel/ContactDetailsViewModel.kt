package io.billie.organisations.presentation.viewmodel

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class ContactDetailsViewModel(
    val id: UUID?,
    @JsonProperty("phone_number") val phone: String,
    val fax: String,
    val email: String,
)
