package com.test.xdata.client

import com.test.xdata.entity.Transaction
import com.test.xdata.repository.TransactionRepository
import com.test.xdata.repository.WalletRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class ScheduledTransactionProcessor(
    private val mockedCryptoCurrencyGateway: MockedCryptoCurrencyGateway,
    private val transactionalOperator: TransactionalOperator,
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
) {

    private val logger = KotlinLogging.logger {}

    @Scheduled(fixedRate = 1000)
    fun processTransactions() {
        val error = mutableSetOf<Long>()

        transactionalOperator.transactional(
            transactionRepository.selectNext100TransferTransactions()
                .flatMap { trx ->
                    mono { mockedCryptoCurrencyGateway.transferMoney(trx) }
                        .map { trx }
                        .doOnError { error.add(trx.id!!) }
                }
                .filter { !error.contains(it.id) }
                .publishOn(Schedulers.boundedElastic())
                .flatMap { trx ->
                    if (trx.operation == Transaction.Operation.WITHDRAWAL) {
                        walletRepository.findByIdForUpdate(trx.fromWalletId)
                            .zipWith(walletRepository.findByIdForUpdate(trx.toWalletId))
                            .publishOn(Schedulers.boundedElastic())
                            .doOnNext { pair ->
                                if (pair.t1.balance < trx.amount) {
                                    logger.error { "${pair.t1.id} doesn't have enough money to withdraw" }
                                    pair.t1.balance.minus(trx.amount)
                                    pair.t2.balance.plus(trx.amount)
                                    walletRepository.saveAll(listOf(pair.t1, pair.t2)).collectList().subscribe()
                                }
                            }.subscribe()
                    }
                    if (trx.operation == Transaction.Operation.DEPOSIT) {
                        walletRepository.findByIdForUpdate(trx.toWalletId)
                            .zipWith(walletRepository.findByIdForUpdate(trx.fromWalletId))
                            .publishOn(Schedulers.boundedElastic())
                            .doOnNext { pair ->
                                if (pair.t2.balance < trx.amount) {
                                    logger.error { "${pair.t2.id} doesn't have enough money to withdraw" }
                                    pair.t1.balance.plus(trx.amount)
                                    pair.t2.balance.minus(trx.amount)
                                    walletRepository.saveAll(listOf(pair.t1, pair.t2)).collectList().subscribe()
                                }
                            }.subscribe()
                    }
                    transactionRepository.save(trx.copy(status = Transaction.Status.PROCESSED))
                }).subscribe()
    }

}