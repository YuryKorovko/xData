package com.test.xdata.service

import com.test.xdata.entity.Wallet
import com.test.xdata.model.dto.WalletDto
import com.test.xdata.model.request.WalletRequest
import com.test.xdata.repository.CurrencyRepository
import com.test.xdata.repository.UserRepository
import com.test.xdata.repository.WalletRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class WalletService(
        private val walletRepository: WalletRepository,
        private val currencyRepository: CurrencyRepository,
        private val userRepository: UserRepository
) {

    @Transactional
    suspend fun createWallet(request: WalletRequest, userId: Long): WalletDto {
        return userRepository.existsById(userId).filter { it }
                .flatMap {
                    currencyRepository.findByName(request.currency)
                            .flatMap {
                                walletRepository.save(Wallet.new(userId, it))
                            }.map {
                                WalletDto(it.id!!, it.balance, it.currency.name)
                            }
                }
                .switchIfEmpty(Mono.error(Exception("Cannot create wallet as user=$userId doesn't exist")))
                .awaitSingle()
    }
}