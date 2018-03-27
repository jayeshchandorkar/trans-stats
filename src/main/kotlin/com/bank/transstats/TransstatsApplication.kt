package com.bank.transstats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TransstatsApplication

fun main(args: Array<String>) {
    runApplication<TransstatsApplication>(*args)
}
