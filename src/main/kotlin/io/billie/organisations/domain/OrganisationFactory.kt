package io.billie.organisations.domain

import io.billie.organisations.domain.dto.CountryDto
import io.billie.organisations.domain.exception.UnableToFindCountryException
import io.billie.organisations.domain.model.ContactDetails
import io.billie.organisations.domain.model.CountryInfo
import io.billie.organisations.domain.model.LegalType
import io.billie.organisations.domain.model.Organisation
import io.billie.organisations.infrastructure.CountryProviderAdapter
import io.billie.organisations.infrastructure.OrganisationDto
import io.billie.organisations.presentation.dto.OrganisationRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class OrganisationFactory {
    @Autowired
    private lateinit var countryProviderAdapter: CountryProviderAdapter
    private val countries: Map<String, CountryDto> by lazy {
        countryProviderAdapter.findAll()
    }
    fun createFromDto(dto: OrganisationDto): Organisation {
        val countryInfo: CountryInfo = getCountry(dto.countryCode)

        val contactDetails: ContactDetails = ContactDetails.Companion.create(
            dto.contactDetailsId,
            dto.phoneNumber,
            dto.fax,
            dto.email
        )
        return Organisation.Companion.create(
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

    fun createFromRequestDto(dto: OrganisationRequestDto): Organisation {
        val countryInfo: CountryInfo = getCountry(dto.countryCode)
        val contactDetails: ContactDetails = ContactDetails.Companion.create(
            null,
            dto.contactDetails.phoneNumber.orEmpty(),
            dto.contactDetails.fax.orEmpty(),
            dto.contactDetails.email.orEmpty()
        )
        return Organisation.Companion.create(
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