package io.billie.orders.infrastructure

import io.billie.merchants.domain.MerchantProviderInterface
import io.billie.merchants.domain.dto.MerchantExposeDto
import java.util.UUID

open class MerchantProviderAdapter(): MerchantProviderInterface {
    override fun findById(id: UUID): MerchantExposeDto? {
        TODO("Not yet implemented")
    }
}