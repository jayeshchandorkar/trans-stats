package com.bank.transstats.services

import com.bank.transstats.models.Statistics
import com.bank.transstats.models.TransactionRequest
import com.bank.transstats.models.TransactionSecondKey
import com.bank.transstats.repositories.RecentTransactions
import org.springframework.stereotype.Service

@Service
class TransStatsService (val recentTransactions: RecentTransactions){

    fun saveTransaction(transactionRequest: TransactionRequest) {
        val transactionSecond = transactionRequest.timestamp / 1000
        val transactionSecondKey = TransactionSecondKey(transactionSecond)

        var transactionSecondSummary = recentTransactions.get(transactionSecondKey)
        if (transactionSecondSummary != null) {
            transactionSecondSummary.sum += transactionRequest.amount
            transactionSecondSummary.count++
            if (transactionRequest.amount > transactionSecondSummary.max) {
                transactionSecondSummary.max = transactionRequest.amount
            }
            if (transactionRequest.amount < transactionSecondSummary.min) {
                transactionSecondSummary.min = transactionRequest.amount
            }
        }
    }

    fun getStatistics(): Statistics {
        return Statistics(0.0, 0.0, 0.0, 0.0, 0)
    }

}