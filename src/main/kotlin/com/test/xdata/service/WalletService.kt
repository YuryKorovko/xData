package com.test.xdata.service

import com.test.xdata.client.MockedCryptoCurrencyGateway
import com.test.xdata.entity.CurrencyEnum
import com.test.xdata.entity.Wallet
import com.test.xdata.model.dto.WalletDto
import com.test.xdata.model.dto.toDto
import com.test.xdata.model.request.WalletRequest
import com.test.xdata.repository.CurrencyRepository
import com.test.xdata.repository.UserRepository
import com.test.xdata.repository.WalletRepository
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val currencyRepository: CurrencyRepository,
    private val userRepository: UserRepository,
    private val cryptoCurrencyGateway: MockedCryptoCurrencyGateway
) {
    @Transactional
    suspend fun createWallet(request: WalletRequest, userId: Long): WalletDto {
        return userRepository.existsById(userId)
            .filter { it }
            .switchIfEmpty(Mono.error(IllegalStateException("Cannot create wallet as user=$userId doesn't exist")))
            .then(mono {
                cryptoCurrencyGateway.createCryptoWallet(request.currency)
            })
            .flatMap { walletId ->
                walletRepository.existsByUserIdAndCurrency(userId, request.currency)
                    .filter { !it }
                    .switchIfEmpty(Mono.error(IllegalStateException("Only 1 ${request.currency} wallet is allowed to have")))
                    .then(
                        currencyRepository.findByName(request.currency)
                            .flatMap {
                                walletRepository.save(Wallet.new(userId, it, walletId.toString()))
                                    .map { wallet -> wallet.toDto() }
                            }
                    )
            }
            .awaitSingle()
    }

    suspend fun getWallets(userId: Long, currencies: List<CurrencyEnum>): List<WalletDto> {
        return walletRepository.findAllByUserIdAndCurrencyIn(userId, currencies)
            .map { it.toDto() }
            .collectList()
            .awaitSingle()
    }
}