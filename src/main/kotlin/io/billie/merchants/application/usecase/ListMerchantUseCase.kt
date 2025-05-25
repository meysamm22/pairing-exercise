package io.billie.merchants.application.usecase

import io.billie.merchants.infrastructure.MerchantRepository
import io.billie.merchants.presentation.viewmodel.ContactDetailsViewModel
import io.billie.merchants.presentation.viewmodel.CountryViewModel
import io.billie.merchants.presentation.viewmodel.MerchantViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ListMerchantUseCase() {
    @Autowired
    private lateinit var organisationRepository: MerchantRepository

    fun findMerchants(): List<MerchantViewModel> {
        return organisationRepository.findAll().map {
                organisation ->
                MerchantViewModel(
                    organisation.id,
                    organisation.name,
                        organisation.dateFounded,
                            CountryViewModel(
                                organisation.country.id,
                                organisation.country.name,
                                organisation.country.code,
                            ),
                        organisation.vatNumber,
                        organisation.registrationNumber,
                        organisation.legalType.name,
                            ContactDetailsViewModel(
                              organisation.contactDetails.id,
                                organisation.contactDetails.phoneNumber,
                                organisation.contactDetails.fax,
                                organisation.contactDetails.email
                            )
                        )
                }
    }
}