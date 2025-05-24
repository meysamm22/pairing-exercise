package io.billie.organisations.domain.repositoryinterface

import io.billie.organisations.domain.model.Organisation
import java.util.UUID

internal interface OrganisationRepositoryInterface {
    fun findAll(): List<Organisation>
    fun create(organisation: Organisation): UUID
}