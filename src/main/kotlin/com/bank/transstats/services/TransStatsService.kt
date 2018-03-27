package com.bank.transstats.services

import com.bank.transstats.models.Statistics
import com.bank.transstats.models.TransactionRequest
import org.springframework.stereotype.Service

@Service
class TransStatsService {
    fun saveTransaction(transactionRequest: TransactionRequest) {}
    fun getStatistics(): Statistics {
        return Statistics(0.0, 0.0, 0.0, 0.0, 0)
    }
}