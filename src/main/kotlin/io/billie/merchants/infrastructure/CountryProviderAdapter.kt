package io.billie.merchants.infrastructure

import io.billie.countries.infrastructure.CountryQueryService
import io.billie.merchants.domain.CountryProviderInterface
import io.billie.merchants.domain.dto.CountryDto
import org.springframework.stereotype.Service

@Service
class CountryProviderAdapter(
    private val countryQueryService: CountryQueryService
) : CountryProviderInterface {
    override fun findAll(): Map<String, CountryDto> {
        return countryQueryService.findAll()
            .associateBy({ it.code }, { CountryDto(it.id, it.name, it.code) })
    }
}