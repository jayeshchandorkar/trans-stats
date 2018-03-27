package com.bank.transstats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransstatsApplication

fun main(args: Array<String>) {
    runApplication<TransstatsApplication>(*args)
}
