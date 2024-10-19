package com.test.xdata.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("currency")
data class Currency(
        @Id
        val name: CurrencyEnum,
        @Column("crypto")
        val isCrypto: Boolean
)

enum class CurrencyEnum {
        BTC,
        ETH,
        USDT,
        USD,
        EUR
}
