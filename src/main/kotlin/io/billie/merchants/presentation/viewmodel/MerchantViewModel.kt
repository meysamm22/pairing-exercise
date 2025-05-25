package io.billie.merchants.presentation.viewmodel

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.util.UUID

data class MerchantViewModel(
    val id: UUID?,
    val name: String,
    @JsonFormat(pattern = "dd/MM/yyyy") @JsonProperty("date_founded") val dateFounded: LocalDate,
    val country: CountryViewModel,
    @JsonProperty("vat_number") val VATNumber: String?,
    @JsonProperty("registration_number") val registrationNumber: String?,
    @JsonProperty("legal_entity_type") val legalEntityType: String,
    @JsonProperty("contact_details") val contactDetails: ContactDetailsViewModel,
)