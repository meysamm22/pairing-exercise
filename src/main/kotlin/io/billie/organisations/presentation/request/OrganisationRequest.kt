package io.billie.organisations.presentation.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import io.billie.organisations.application.dto.OrganisationRequestDto
import java.time.LocalDate
import javax.validation.constraints.NotBlank

data class OrganisationRequest(
    @field:NotBlank val name: String,
    @JsonFormat(pattern = "dd/MM/yyyy") @JsonProperty("date_founded") val dateFounded: LocalDate,
    @field:NotBlank @JsonProperty("country_code") val countryCode: String,
    @JsonProperty("vat_number") val VATNumber: String?,
    @JsonProperty("registration_number") val registrationNumber: String?,
    @JsonProperty("legal_entity_type") val legalEntityType: String,
    @JsonProperty("contact_details") val contactDetails: ContactDetailsRequest,
) {
    fun toOrganisationRequestDto(): OrganisationRequestDto {
        return OrganisationRequestDto(
            name,
            dateFounded,
            countryCode,
            VATNumber.orEmpty(),
            registrationNumber.orEmpty(),
            legalEntityType,
            contactDetails.toContactDetailsRequestDto()
        )
    }
}