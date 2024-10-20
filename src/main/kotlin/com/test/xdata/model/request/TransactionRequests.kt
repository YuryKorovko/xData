package com.test.xdata.model.request

import com.test.xdata.entity.CurrencyEnum
import com.test.xdata.validation.CryptoCurrency
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotEmpty
import java.math.BigDecimal

data class TransactionRequest(
    @DecimalMin(value = "0.0001")
    val amount: BigDecimal,
    @CryptoCurrency(message = "Only crypto currency allowed")
    val currency: CurrencyEnum,
    @NotEmpty
    val fromWallet: Long,
    @NotEmpty
    val toWallet: Long
)