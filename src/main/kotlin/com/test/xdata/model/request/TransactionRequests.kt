package com.test.xdata.model.request

import com.test.xdata.entity.CurrencyEnum
import java.math.BigDecimal

data class TransferRequest(
        val amount: BigDecimal,
        val currency: CurrencyEnum,
        val userId: Long
)