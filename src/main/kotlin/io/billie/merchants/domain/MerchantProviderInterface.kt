package io.billie.merchants.domain

import io.billie.merchants.domain.dto.MerchantExposeDto
import java.util.UUID

internal interface MerchantProviderInterface {
    fun findById(id: UUID): MerchantExposeDto?
}