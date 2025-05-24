package io.billie.countries.infrastructure

import io.billie.countries.application.CountryQueryServiceInterface
import io.billie.countries.application.dto.CountryDto
import io.billie.countries.domain.repositoryinterface.CountryRepositoryInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CountryQueryService(): CountryQueryServiceInterface {
    @Autowired
    private lateinit var repository: CountryRepositoryInterface

    override fun findAll(): List<CountryDto> {
        return repository.findAll().map {
            country -> CountryDto(country.id, country.name, country.code)
        }
    }
}