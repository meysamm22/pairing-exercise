package io.billie.merchants.application.usecase

import io.billie.merchants.infrastructure.MerchantRepository
import io.billie.merchants.domain.MerchantFactory
import io.billie.merchants.domain.model.Merchant
import io.billie.merchants.application.dto.MerchantRequestDto
import io.billie.merchants.presentation.viewmodel.CreatedMerchantViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreateMerchantUseCase() {
    @Autowired
    private lateinit var merchantFactory: MerchantFactory

    @Autowired
    private lateinit var organisationRepository: MerchantRepository

    fun createMerchant(dto: MerchantRequestDto): CreatedMerchantViewModel {
        val merchant: Merchant = merchantFactory.createFromRequestDto(dto)
        return CreatedMerchantViewModel(organisationRepository.create(merchant));
    }

}