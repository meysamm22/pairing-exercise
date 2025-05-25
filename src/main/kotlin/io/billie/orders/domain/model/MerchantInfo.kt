package io.billie.orders.domain.model

import java.util.UUID

internal class MerchantInfo private constructor(
    private val _id: UUID,
    private val _name: String
) {
    val id: UUID get() = _id
    val name: String get() = _name

    internal companion object {
        fun create(
            id: UUID,
            name: String
        ): MerchantInfo {
            return MerchantInfo(id, name)
        }
    }
}
