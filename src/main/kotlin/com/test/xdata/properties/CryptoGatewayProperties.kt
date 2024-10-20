package com.test.xdata.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("crypto.gateway")
data class CryptoGatewayProperties(
    val btc: String,
    val eth: String,
    val usdt: String,
)
