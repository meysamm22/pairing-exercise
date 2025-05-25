package io.billie.merchants.domain

import io.billie.merchants.domain.dto.CountryDto
import io.billie.merchants.domain.exception.UnableToFindCountryException
import io.billie.merchants.domain.model.ContactDetails
import io.billie.merchants.domain.model.CountryInfo
import io.billie.merchants.domain.model.LegalType
import io.billie.merchants.domain.model.Merchant
import io.billie.merchants.infrastructure.CountryProviderAdapter
import io.billie.merchants.infrastructure.MerchantDatabaseDto
import io.billie.merchants.application.dto.MerchantRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class MerchantFactory {
    @Autowired
    private lateinit var countryProviderAdapter: CountryProviderAdapter
    private val countries: Map<String, CountryDto> by lazy {
        countryProviderAdapter.findAll()
    }
    fun createFromDto(dto: MerchantDatabaseDto): Merchant {
        val countryInfo: CountryInfo = getCountry(dto.countryCode)

        val contactDetails: ContactDetails = ContactDetails.Companion.create(
            dto.contactDetailsId,
            dto.phoneNumber,
            dto.fax,
            dto.email
        )
        return Merchant.Companion.create(
            dto.id,
            dto.name,
            dto.dateFounded,
            countryInfo,
            dto.vatNumber,
            dto.registrationNumber,
            LegalType.valueOf(dto.legalEntityType),
            contactDetails
        )
    }

    fun createFromRequestDto(dto: MerchantRequestDto): Merchant {
        val countryInfo: CountryInfo = getCountry(dto.countryCode)
        val contactDetails: ContactDetails = ContactDetails.Companion.create(
            null,
            dto.contactDetails.phoneNumber.orEmpty(),
            dto.contactDetails.fax.orEmpty(),
            dto.contactDetails.email.orEmpty()
        )
        return Merchant.Companion.create(
            null,
            dto.name,
            dto.dateFounded,
            countryInfo,
            dto.VATNumber.orEmpty(),
            dto.registrationNumber.orEmpty(),
            LegalType.valueOf(dto.legalEntityType),
            contactDetails
        )
    }

    private fun getCountry(countryCode: String): CountryInfo {
        val countryDto: CountryDto
        try {
            countryDto = countries.getValue(countryCode)
        } catch (e: NoSuchElementException) {
            throw UnableToFindCountryException(countryCode);
        }

        return CountryInfo.create(
            countryDto.id,
            countryDto.name,
            countryDto.code
        )
    }
}