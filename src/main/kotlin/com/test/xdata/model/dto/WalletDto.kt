package com.test.xdata.model.dto

import com.test.xdata.entity.CurrencyEnum
import com.test.xdata.entity.Wallet
import java.math.BigDecimal

data class WalletDto(
        val id: Long,
        val balance: BigDecimal,
        val currencyEnum: CurrencyEnum
)

fun Wallet.toDto() = WalletDto(
        this.id!!,
        this.balance,
        this.currency,
)
