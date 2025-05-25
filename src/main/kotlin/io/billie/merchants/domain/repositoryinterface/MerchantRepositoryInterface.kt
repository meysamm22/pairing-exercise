package io.billie.merchants.domain.repositoryinterface

import io.billie.merchants.domain.model.Merchant
import java.util.UUID

internal interface MerchantRepositoryInterface {
    fun findAll(): List<Merchant>
    fun create(merchant: Merchant): UUID
    fun findById(id: UUID): Merchant?
}