package com.test.xdata.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table


@Table("users")
data class User(
        @Id
        val id: Long,
        @Column
        val email: String,
        @Column
        val password: String,
        @Column("wallet_id")
        val walletIds: List<Wallet>
)