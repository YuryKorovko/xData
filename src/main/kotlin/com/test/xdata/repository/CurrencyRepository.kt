package com.test.xdata.repository

import com.test.xdata.entity.Currency
import com.test.xdata.entity.CurrencyEnum
import com.test.xdata.entity.Wallet
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface CurrencyRepository: ReactiveCrudRepository<Currency, Long> {
  fun findByName(currencyEnum: CurrencyEnum): Mono<Currency>
}