package io.billie.countries.domain.repositoryinterface

import io.billie.countries.domain.model.Country

internal interface CountryRepositoryInterface {
    fun findAll(): List<Country>
}