package io.billie.organisations.application.usecase

import io.billie.organisations.infrastructure.OrganisationRepository
import io.billie.organisations.presentation.viewmodel.ContactDetailsViewModel
import io.billie.organisations.presentation.viewmodel.CountryViewModel
import io.billie.organisations.presentation.viewmodel.OrganisationViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ListOrganisationUseCase() {
    @Autowired
    private lateinit var organisationRepository: OrganisationRepository

    fun findOrganisations(): List<OrganisationViewModel> {
        return organisationRepository.findAll().map {
                organisation ->
                OrganisationViewModel(
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