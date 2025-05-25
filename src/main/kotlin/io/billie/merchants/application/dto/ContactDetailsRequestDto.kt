package io.billie.merchants.application.dto


data class ContactDetailsRequestDto(
    val phoneNumber: String,
    val fax: String,
    val email: String
)