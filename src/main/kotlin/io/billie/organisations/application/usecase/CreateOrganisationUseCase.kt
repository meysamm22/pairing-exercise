package io.billie.organisations.application.usecase

import io.billie.organisations.infrastructure.OrganisationRepository
import io.billie.organisations.domain.OrganisationFactory
import io.billie.organisations.domain.model.Organisation
import io.billie.organisations.application.dto.OrganisationRequestDto
import io.billie.organisations.presentation.viewmodel.CreatedOrganisationViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreateOrganisationUseCase() {
    @Autowired
    private lateinit var organisationFactory: OrganisationFactory

    @Autowired
    private lateinit var organisationRepository: OrganisationRepository

    fun createOrganisation(dto: OrganisationRequestDto): CreatedOrganisationViewModel {
        val organisation: Organisation = organisationFactory.createFromRequestDto(dto)
        return CreatedOrganisationViewModel(organisationRepository.create(organisation));
    }

}