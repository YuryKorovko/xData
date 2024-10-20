package com.test.xdata.controller

import com.test.xdata.entity.CurrencyEnum
import com.test.xdata.model.request.WalletRequest
import com.test.xdata.service.WalletService
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/wallet")
class WalletController(
    private val walletService: WalletService
) {

    @PutMapping
    suspend fun createWallet(
        @RequestBody request: WalletRequest,
        @RequestHeader(X_USER_ID_HEADER) userId: Long
    ) = coroutineScope {
        walletService.createWallet(request, userId)
    }
    @GetMapping
    suspend fun getWallet(
        @RequestParam(name = "currencies", defaultValue = "BTC,ETH,USDT") currencies: List<CurrencyEnum>,
        @RequestHeader(X_USER_ID_HEADER) userId: Long
    ) = coroutineScope {
        walletService.getWallets(userId, currencies)
    }
}