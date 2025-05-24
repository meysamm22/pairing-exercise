package io.billie.organisations.domain.model

import java.util.UUID

internal class CountryInfo private constructor(
    private val _id: UUID,
    private val _name: String,
    private val _code: String
) {
    val id: UUID get() = _id
    val name: String get() = _name
    val code: String get() = _code

    internal companion object{
        fun create(
            id: UUID,
            name: String,
            code: String
        ): CountryInfo {
            return CountryInfo(
                id,
                name,
                code
            )
        }
    }
}