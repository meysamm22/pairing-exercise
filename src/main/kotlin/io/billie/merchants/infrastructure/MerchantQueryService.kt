package io.billie.merchants.infrastructure

import io.billie.merchants.domain.MerchantProviderInterface
import io.billie.merchants.domain.dto.MerchantExposeDto
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

class MerchantQueryService() : MerchantProviderInterface {

    @Autowired
    private lateinit var repository: MerchantRepository

    override fun findById(id: UUID): MerchantExposeDto? {
        return null //TODO:: Will be implemented after unit tests
    }
}