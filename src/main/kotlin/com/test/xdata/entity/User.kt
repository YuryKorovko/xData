package com.test.xdata.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.crypto.bcrypt.BCrypt


@Table("users")
data class User(
    @Id
    @Column("id")
    val id: Long,
    @Column("email")
    val email: String,
    @Column("password")
    val password: String
) {
    companion object {
        fun new(email: String, password: String) = User(
            id = 0,
            email = email,
            password = BCrypt.hashpw(password, BCrypt.gensalt())
        )
    }
}