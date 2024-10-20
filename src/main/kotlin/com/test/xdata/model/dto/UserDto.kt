package com.test.xdata.model.dto

data class UserDto (
    val id: Long,
    val email: String,
    val wallets: List<WalletDto>
)