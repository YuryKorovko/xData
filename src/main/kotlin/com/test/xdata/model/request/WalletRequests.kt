package com.test.xdata.model.request

import com.test.xdata.entity.CurrencyEnum

data class WalletRequest(
    val currency: CurrencyEnum
)