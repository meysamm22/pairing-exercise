package io.billie.merchants.application.dto

import java.time.LocalDate

data class MerchantRequestDto(
    val name: String,
    val dateFounded: LocalDate,
    val countryCode: String,
    val VATNumber: String,
    val registrationNumber: String,
    val legalEntityType: String,
    val contactDetails: ContactDetailsRequestDto,
)