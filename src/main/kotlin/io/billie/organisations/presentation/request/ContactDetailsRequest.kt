package io.billie.organisations.presentation.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.billie.organisations.application.dto.ContactDetailsRequestDto

data class ContactDetailsRequest(
    @JsonProperty("phone_number") val phoneNumber: String?,
    val fax: String?,
    val email: String?
) {
    fun toContactDetailsRequestDto(): ContactDetailsRequestDto {
        return ContactDetailsRequestDto(
            phoneNumber.orEmpty(),
            fax.orEmpty(),
            email.orEmpty()
        )
    }
}