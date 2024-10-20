package com.test.xdata.repository

import com.test.xdata.entity.Transaction
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface TransactionRepository: ReactiveCrudRepository<Transaction, Long> {

    @Query("select * from transactions t where t.status = 'NEW' and t.operation in ('DEPOSIT', 'WITHDRAWAL') order by t.created_at limit 100 for update skip locked")
    fun selectNext100TransferTransactions(): Flux<Transaction>

}