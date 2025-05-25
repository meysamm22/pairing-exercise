package io.billie.orders.infrastructure

import io.billie.merchants.domain.MerchantProviderInterface
import io.billie.merchants.domain.dto.MerchantExposeDto
import io.billie.merchants.infrastructure.MerchantQueryService
import java.util.UUID

open class  MerchantProviderAdapter(
    val merchantQueryService: MerchantQueryService
): MerchantProviderInterface {
    override fun findById(id: UUID): MerchantExposeDto? {
        return merchantQueryService.findById(id)
    }
}