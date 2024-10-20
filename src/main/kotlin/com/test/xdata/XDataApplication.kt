package com.test.xdata

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableWebFluxSecurity
@EnableScheduling
class XDataApplication

fun main(args: Array<String>) {
    runApplication<XDataApplication>(*args)
}
