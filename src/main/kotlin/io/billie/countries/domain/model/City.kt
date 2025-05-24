package io.billie.countries.domain.model

import io.billie.countries.domain.exception.InvalidCityInfoException
import java.util.UUID

internal class City private constructor(
    private val _id: UUID,
    private val _name: String,
    private val _countryCode: String
){

    val id: UUID get() = _id
    val name: String get() = _name
    val countryCode: String get() = _countryCode

    internal companion object {
        fun create(id: UUID, name: String, countryCode: String): City {
            if (name.isBlank()) {
                throw InvalidCityInfoException("City name cannot be empty");
            }

            if (countryCode.matches(Regex("[a-z]{2}"))) {
                throw InvalidCityInfoException("Country code should be 2 character");
            }
            return City(id, name.trim(), countryCode.uppercase())
        }
    }
}