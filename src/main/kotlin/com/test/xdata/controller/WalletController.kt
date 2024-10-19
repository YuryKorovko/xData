package com.test.xdata.controller

import com.test.xdata.model.request.WalletRequest
import com.test.xdata.service.WalletService
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/wallet")
class WalletController(
        private val walletService: WalletService
) {

  @PutMapping
  suspend fun createWallet(
          @RequestBody request: WalletRequest,
          @RequestHeader(value = "X-User-Id") userId: Long
  ) = coroutineScope {
    walletService.createWallet(request, userId)
  }
}