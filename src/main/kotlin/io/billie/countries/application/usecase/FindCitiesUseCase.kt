package io.billie.countries.application.usecase

import io.billie.countries.domain.repositoryinterface.CityRepositoryInterface
import io.billie.countries.presentation.viewmodel.CityViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FindCitiesUseCase() {

    @Autowired
    private lateinit var cityRepository: CityRepositoryInterface

    fun findByCountryCode(countryCode: String): List<CityViewModel> {
        return cityRepository.findAllByCountryCode(countryCode)
            .map {
                city ->
                CityViewModel(city.id.toString(), city.name, city.countryCode)
            }
    }

}