package com.bank.transstats.models

data class TransactionRequest(val amount: Double,
                              val timestamp: Long)