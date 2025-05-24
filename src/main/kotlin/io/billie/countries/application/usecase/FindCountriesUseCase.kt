package io.billie.countries.application.usecase

import io.billie.countries.domain.repositoryinterface.CountryRepositoryInterface
import io.billie.countries.presentation.viewmodel.CountryViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FindCountriesUseCase() {

    @Autowired
    private lateinit var countryRepository: CountryRepositoryInterface

    fun findCountries(): List<CountryViewModel> {
        return countryRepository.findAll().map{
            country ->
            CountryViewModel(
                country.id.toString(),
                country.name,
                country.code
            )
        }
    }
}