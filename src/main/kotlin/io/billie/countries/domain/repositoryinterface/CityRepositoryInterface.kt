package io.billie.countries.domain.repositoryinterface

import io.billie.countries.domain.model.City

internal interface CityRepositoryInterface {
    fun findAllByCountryCode(countryCode: String): List<City>
}