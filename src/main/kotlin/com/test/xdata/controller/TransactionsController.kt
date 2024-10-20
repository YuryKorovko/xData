package com.test.xdata.controller

import com.test.xdata.model.request.TransactionRequest
import com.test.xdata.service.TransactionsService
import com.test.xdata.service.WalletService
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionsController(
    private val transactionsService: TransactionsService
) {

    @PostMapping("/deposit")
    suspend fun depositMoney(
        @RequestBody @Valid request: TransactionRequest,
        @RequestHeader(X_USER_ID_HEADER) userId: Long
    ) = coroutineScope {
        transactionsService.depositMoney(request, userId)
    }

    @PostMapping("/withdrawal")
    suspend fun withdrawalMoney(@RequestBody @Valid request: TransactionRequest,
                                @RequestHeader(X_USER_ID_HEADER) userId: Long) = coroutineScope {
        transactionsService.withdrawalMoney(request, userId)
    }
}