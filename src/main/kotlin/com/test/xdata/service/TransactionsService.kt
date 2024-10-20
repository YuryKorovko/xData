package com.test.xdata.service

import com.test.xdata.entity.Transaction
import com.test.xdata.model.dto.WalletDto
import com.test.xdata.model.dto.toDto
import com.test.xdata.model.request.TransactionRequest
import com.test.xdata.repository.TransactionRepository
import com.test.xdata.repository.WalletRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class TransactionsService(
    private val walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository
) {
    @Transactional
    suspend fun depositMoney(request: TransactionRequest, userId: Long): WalletDto {
        return walletRepository.findByUserIdAndCurrencyAndId(userId, request.currency, request.toWallet)
            .switchIfEmpty(Mono.error(IllegalStateException("Wallet=${request.toWallet} doesn't exist")))
            .flatMap { wallet ->
                transactionRepository.save(
                    Transaction.new(
                        request.amount,
                        request.toWallet,
                        request.fromWallet,
                        Transaction.Operation.DEPOSIT,
                        request.currency
                    )
                ).map {
                    wallet.toDto()
                }
            }.awaitSingle()
    }

    @Transactional
    suspend fun withdrawalMoney(request: TransactionRequest, userId: Long): WalletDto {
        return walletRepository.findByUserIdAndCurrencyAndId(userId, request.currency, request.toWallet)
            .switchIfEmpty(Mono.error(IllegalStateException("Wallet=${request.toWallet} doesn't exist")))
            .flatMap { wallet ->
                transactionRepository.save(
                    Transaction.new(
                        request.amount,
                        request.toWallet,
                        request.fromWallet,
                        Transaction.Operation.WITHDRAWAL,
                        request.currency
                    )
                ).map {
                    wallet.toDto()
                }
            }.awaitSingle()
    }
}