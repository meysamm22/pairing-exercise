package io.billie.merchants.presentation.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import io.billie.merchants.application.dto.MerchantRequestDto
import java.time.LocalDate
import javax.validation.constraints.NotBlank

data class MerchantRequest(
    @field:NotBlank val name: String,
    @JsonFormat(pattern = "dd/MM/yyyy") @JsonProperty("date_founded") val dateFounded: LocalDate,
    @field:NotBlank @JsonProperty("country_code") val countryCode: String,
    @JsonProperty("vat_number") val VATNumber: String?,
    @JsonProperty("registration_number") val registrationNumber: String?,
    @JsonProperty("legal_entity_type") val legalEntityType: String,
    @JsonProperty("contact_details") val contactDetails: ContactDetailsRequest,
) {
    fun toMerchantRequestDto(): MerchantRequestDto {
        return MerchantRequestDto(
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