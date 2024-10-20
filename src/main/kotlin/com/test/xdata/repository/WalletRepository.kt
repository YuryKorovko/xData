package com.test.xdata.repository

import com.test.xdata.entity.CurrencyEnum
import com.test.xdata.entity.Wallet
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.relational.core.sql.LockMode
import org.springframework.data.relational.repository.Lock
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface WalletRepository: ReactiveCrudRepository<Wallet, Long> {
    @Lock(LockMode.PESSIMISTIC_WRITE)
    fun findByUserIdAndCurrency(userId: Long, currencyEnum: CurrencyEnum): Mono<Wallet>

    fun findAllByUserIdAndCurrencyIn(userId: Long, currency: List<CurrencyEnum>): Flux<Wallet>

    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Query("select * from wallets w where w.id = :address")
    fun findByIdForUpdate(id: Long): Mono<Wallet>

    fun findAllByUserId(userId: Long): Flux<Wallet>

    fun findByUserIdAndCurrencyAndId(userId: Long, currency: CurrencyEnum, id: Long): Mono<Wallet>
    fun existsByUserIdAndCurrency(userId: Long, currency: CurrencyEnum): Mono<Boolean>
}