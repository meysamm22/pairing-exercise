package io.billie.merchants.domain.exception

class UnableToFindCountryException(val countryCode: String) : RuntimeException()