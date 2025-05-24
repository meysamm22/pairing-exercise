package io.billie.organisations.domain.exception

class UnableToFindCountryException(val countryCode: String) : RuntimeException()