package io.billie.merchants.infrastructure

import java.time.LocalDate
import java.util.UUID

data class MerchantDatabaseDto(
    val id: UUID,
    val name: String,
    val dateFounded: LocalDate,
    val countryCode: String,
    val vatNumber: String,
    val registrationNumber: String,
    val legalEntityType: String,
    val contactDetailsId: UUID,
    val fax: String,
    val email: String,
    val phoneNumber: String,
)
