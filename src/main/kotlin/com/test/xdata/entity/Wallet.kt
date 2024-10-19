package com.test.xdata.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("wallets")
data class Wallet(
        @Id
        val id: Long? = null,
        @Column
        val userId: Long,
        @Column
        val currency: Currency,
        @Column
        val balance: BigDecimal,
) {
  companion object {
    fun new(userId: Long, currency: Currency) = Wallet(
            userId = userId,
            currency = currency,
            balance = BigDecimal.ZERO
    )
  }
}
