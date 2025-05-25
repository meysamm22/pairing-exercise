package io.billie.merchants.domain.model

import io.billie.merchants.domain.exception.InvalidMerchantInfoException
import java.time.LocalDate
import java.util.UUID

class Merchant private constructor(
    private val _id: UUID?,
    private val _name: String,
    private val _dateFounded: LocalDate,
    private val _country: CountryInfo,
    private val _vatNumber: String,
    private val _registrationNumber: String,
    private val _legalType: LegalType,
    private val _contactDetails: ContactDetails,
) {
    val id: UUID? get() = _id
    val name: String get() = _name
    val dateFounded: LocalDate get() = _dateFounded
    val country: CountryInfo get() = _country
    val vatNumber: String get() = _vatNumber
    val registrationNumber: String get() = _registrationNumber
    val legalType: LegalType get() = _legalType
    val contactDetails: ContactDetails get() = _contactDetails

    internal companion object {
        fun create(
            id: UUID?,
            name: String,
            dateFounded: LocalDate,
            country: CountryInfo,
            vatNumber: String,
            registrationNumber: String,
            legalType: LegalType,
            contactDetails: ContactDetails
        ): Merchant {

            if (name.isBlank()) {
                throw InvalidMerchantInfoException("Name must not be blank")
            }

            if (dateFounded.isAfter(LocalDate.now())) {
                throw InvalidMerchantInfoException("Founded date must be in the past")
            }

            return Merchant(
                id, name, dateFounded, country,
                vatNumber, registrationNumber,
                legalType, contactDetails
            )
        }
    }
}