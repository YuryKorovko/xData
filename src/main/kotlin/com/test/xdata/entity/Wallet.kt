package com.test.xdata.entity

import java.math.BigDecimal
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("wallets")
data class Wallet(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("user_id")
    val userId: Long,
    @Column("currency")
    val currency: CurrencyEnum,
    @Column("balance")
    val balance: BigDecimal,
    @Column("network_id")
    val networkId: String,
) {
    companion object {
        fun new(userId: Long, currency: Currency, networkId: String) = Wallet(
            userId = userId,
            currency = currency.name,
            balance = BigDecimal.ZERO,
            networkId = networkId
        )
    }
}
