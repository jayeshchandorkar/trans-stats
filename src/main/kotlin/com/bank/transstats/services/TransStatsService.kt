package com.bank.transstats.services

import com.bank.transstats.models.Statistics
import com.bank.transstats.models.TransactionRequest
import com.bank.transstats.models.TransactionSecondKey
import com.bank.transstats.models.TransactionSecondSummary
import com.bank.transstats.repositories.RecentTransactions
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class TransStatsService (val recentTransactions: RecentTransactions){

    /** Stores transaction in O(1) */
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

    /** Returns statistics in O(1) */
    fun getStatistics(): Statistics {
        val currentTimeInSeconds = System.currentTimeMillis() / 1000
        var count = 0
        var sum = 0.0
        var max = Double.NEGATIVE_INFINITY
        var min = Double.POSITIVE_INFINITY

        for (i in 0..59) {
            val transactionSecondKey = TransactionSecondKey(currentTimeInSeconds - i)
            val transactionSecondSummary = recentTransactions.get(transactionSecondKey)
            if (transactionSecondSummary != null) {
                count += transactionSecondSummary.count
                sum += transactionSecondSummary.sum

                if (transactionSecondSummary.max != Double.NEGATIVE_INFINITY && transactionSecondSummary.max > max) {
                    max = transactionSecondSummary.max
                }
                if (transactionSecondSummary.min != Double.POSITIVE_INFINITY && transactionSecondSummary.min < min) {
                    min = transactionSecondSummary.min
                }
            }
        }
        return prepareStatistics(sum, count, max, min)
    }

    private fun prepareStatistics(sum: Double, count: Int, max: Double, min: Double): Statistics {
        var statMax = 0.0
        var statMin = 0.0
        var statAvg = 0.0

        if (max != Double.NEGATIVE_INFINITY) {
            statMax = max
        }
        if (min != Double.POSITIVE_INFINITY) {
            statMin = min
        }
        if (count != 0 && sum != 0.0) {
            statAvg = sum / count
        }

        return Statistics(sum, statAvg, statMax, statMin, count)
    }

    /** Runs every second to remove 61st old second data and add new for next second */
    @Scheduled(cron = "*/1 * * * * *")
    fun shiftSecondData() {
        val currentTimeInSeconds = System.currentTimeMillis() / 1000
        val staleTransactionSecondKey = TransactionSecondKey(currentTimeInSeconds - 60)
        val newTransactionSecondKey = TransactionSecondKey(currentTimeInSeconds + 1)
        recentTransactions.remove(staleTransactionSecondKey)
        recentTransactions.put(newTransactionSecondKey, TransactionSecondSummary())
    }
}