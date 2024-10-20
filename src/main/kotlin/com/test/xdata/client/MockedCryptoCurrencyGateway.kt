package com.test.xdata.client

import com.test.xdata.entity.CurrencyEnum
import com.test.xdata.entity.Transaction
import com.test.xdata.properties.CryptoGatewayProperties
import com.test.xdata.repository.TransactionRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import kotlinx.coroutines.reactor.mono
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator

@Service
class MockedCryptoCurrencyGateway(
    private val cryptoGatewayProperties: CryptoGatewayProperties
) {
    private val logger = KotlinLogging.logger {}

    suspend fun createCryptoWallet(currency: CurrencyEnum): UUID {
        return when (currency) {
            CurrencyEnum.BTC -> {
                logger.info { "Creating wallet in BTC ${cryptoGatewayProperties.btc}" }
                UUID.randomUUID()
            }

            CurrencyEnum.ETH -> {
                logger.info { "Creating wallet in ETH ${cryptoGatewayProperties.eth}" }
                UUID.randomUUID()
            }

            CurrencyEnum.USDT -> {
                logger.info { "Creating wallet in USDT ${cryptoGatewayProperties.usdt}" }
                UUID.randomUUID()
            }

            else -> {
                throw IllegalStateException("$currency is not allowed to be used in crypto network")
            }
        }
    }

    fun transferMoney(request: Transaction) {
        when (request.currency) {
            CurrencyEnum.BTC -> {
                logger.info { "Transferring money from=$${request.fromWalletId} to=${request.toWalletId} wallet in BTC ${cryptoGatewayProperties.btc}" }
            }

            CurrencyEnum.ETH -> {
                logger.info { "Transferring money from=$${request.fromWalletId} to=${request.toWalletId} wallet in ETH ${cryptoGatewayProperties.eth}" }
            }

            CurrencyEnum.USDT -> {
                logger.info { "Transferring money from=$${request.fromWalletId} to=${request.toWalletId} wallet in USDT ${cryptoGatewayProperties.usdt}" }
            }

            else -> {
                throw IllegalStateException("${request.currency} is not allowed to be used in crypto network")
            }
        }
    }

}