package io.billie.merchants.infrastructure

import io.billie.merchants.domain.MerchantProviderInterface
import io.billie.merchants.domain.dto.MerchantExposeDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MerchantQueryService() : MerchantProviderInterface {

    @Autowired
    private lateinit var repository: MerchantRepository

    override fun findById(id: UUID): MerchantExposeDto? {
        val merchant = repository.findById(id)
        if (merchant == null) {
            return null
        }

        return MerchantExposeDto(merchant.id, merchant.name)
    }
}