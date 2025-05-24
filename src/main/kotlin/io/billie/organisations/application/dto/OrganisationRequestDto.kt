package io.billie.organisations.application.dto

import java.time.LocalDate

data class OrganisationRequestDto(
    val name: String,
    val dateFounded: LocalDate,
    val countryCode: String,
    val VATNumber: String,
    val registrationNumber: String,
    val legalEntityType: String,
    val contactDetails: ContactDetailsRequestDto,
)