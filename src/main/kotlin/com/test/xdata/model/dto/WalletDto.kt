package com.test.xdata.model.dto

import com.test.xdata.entity.CurrencyEnum
import java.math.BigDecimal

data class WalletDto(
        val id: Long,
        val balance: BigDecimal,
        val currencyEnum: CurrencyEnum
)
