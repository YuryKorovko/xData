package com.test.xdata.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("currency")
data class Currency(
    @Id
    @Column("name")
    val name: CurrencyEnum,
    @Column("crypto")
    val isCrypto: Boolean
)

enum class CurrencyEnum {
    BTC,
    ETH,
    USDT,
    USD,
    EUR;

    fun isCrypto() = CRYPTO_CURRENCIES.contains(this)

    companion object {
        val CRYPTO_CURRENCIES = setOf(BTC, ETH, USDT)
    }
}
