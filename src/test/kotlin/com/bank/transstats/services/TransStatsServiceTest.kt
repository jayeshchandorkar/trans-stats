package com.bank.transstats.services

import com.bank.transstats.models.Statistics
import com.bank.transstats.models.TransactionRequest
import com.bank.transstats.models.TransactionSecondKey
import com.bank.transstats.models.TransactionSecondSummary
import com.bank.transstats.repositories.RecentTransactions
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Test

class TransStatsServiceTest {

    private val recentTransactions = mock<RecentTransactions>()
    private val transStatsService = TransStatsService(recentTransactions)


    @Test
    fun saveTransaction_storesTransaction() {
        val transactionSecondSummary = TransactionSecondSummary()
        given(recentTransactions.get(any())).willReturn(transactionSecondSummary)

        val currentTimeInMillis = System.currentTimeMillis()
        val expectedTransactionSecondSummary = TransactionSecondSummary(1, 100.0, 100.0, 100.0)

        val transactionRequest = TransactionRequest(100.0, currentTimeInMillis)

        transStatsService.saveTransaction(transactionRequest)

        assertEquals(transactionSecondSummary, expectedTransactionSecondSummary)
        verify(recentTransactions).get(TransactionSecondKey(currentTimeInMillis/1000))
    }

    @Test
    fun getStatistics_returnsStatisticsForLast60Seconds() {
        val expectedStatistics = Statistics(360.0, 3.0, 4.0, 2.0, 120)
        val transactionSecondSummary = TransactionSecondSummary(2, 6.0, 4.0, 2.0)
        given(recentTransactions.get(any())).willReturn(transactionSecondSummary)

        val statistics = transStatsService.getStatistics()

        assertEquals(statistics, expectedStatistics)
        verify(recentTransactions, times(60)).get(any())
    }

    @Test
    fun getStatistics_noTransactions_returnsNoStatisticsDataForLast60Seconds() {
        val expectedStatistics = Statistics(0.0, 0.0, 0.0, 0.0, 0)
        given(recentTransactions.get(any())).willReturn(TransactionSecondSummary())

        val statistics = transStatsService.getStatistics()

        assertEquals(statistics, expectedStatistics)
        verify(recentTransactions, times(60)).get(any())
    }


}