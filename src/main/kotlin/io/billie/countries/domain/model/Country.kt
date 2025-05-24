package io.billie.countries.domain.model

import io.billie.countries.domain.exception.InvalidCountryInfoException
import java.util.UUID

internal class Country private constructor(
    private val _id: UUID,
    private var _name: String,
    private var _code: String
){
    val id: UUID get() = _id
    val name: String get() = _name
    val code: String get() = _code

    internal companion object {
        fun create(id: UUID, name: String, code: String): Country {
            if (name.isBlank()) {
                throw InvalidCountryInfoException("Country name cannot be empty");
            }

            if (code.matches(Regex("[a-z]{2}"))) {
                throw InvalidCountryInfoException("Country code should be 2 character");
            }
            return Country(id, name.trim(), code.uppercase())
        }
    }
}