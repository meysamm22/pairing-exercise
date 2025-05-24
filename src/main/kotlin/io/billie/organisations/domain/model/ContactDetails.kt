package io.billie.organisations.domain.model

import java.util.UUID

class ContactDetails private constructor(
    private val _id: UUID?,
    private val _phoneNumber: String,
    private val _fax: String,
    private val _email: String
) {
    val id: UUID? get() = _id
    val phoneNumber: String get() = _phoneNumber
    val fax: String get() = _fax
    val email: String get() = _email

    internal companion object {
        fun create(
            id: UUID?,
            phoneNumber: String,
            fax: String,
            email: String,
        ): ContactDetails{
            return ContactDetails(
                id,phoneNumber, fax, email
            )
        }
    }
}