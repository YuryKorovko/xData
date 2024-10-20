package com.test.xdata.entity

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("transactions")
data class Transaction (
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("amount")
    val amount: BigDecimal,
    @Column("created_at")
    val createdAt: Instant,
    @Column("to_wallet_id")
    val toWalletId: Long,
    @Column("from_wallet_id")
    val fromWalletId: Long,
    @Column("status")
    val status: Status,
    @Column("operation")
    val operation: Operation,
    @Column("currency")
    val currency: CurrencyEnum
) {
    enum class Status {
        NEW,
        PROCESSED
    }
    enum class Operation {
        DEPOSIT,
        WITHDRAWAL,
        TRADE
    }

    companion object {
        fun new(amount: BigDecimal, toWallet: Long, fromWallet: Long, operation: Operation, currency: CurrencyEnum) = Transaction(
            amount = amount,
            createdAt = Instant.now(),
            toWalletId = toWallet,
            fromWalletId = fromWallet,
            status = Status.NEW,
            operation = operation,
            currency = currency
        )
    }
}

