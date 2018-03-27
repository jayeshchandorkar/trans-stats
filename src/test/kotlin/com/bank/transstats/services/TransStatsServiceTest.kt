package com.bank.transstats.services

import com.bank.transstats.models.TransactionRequest
import com.bank.transstats.models.TransactionSecondKey
import com.bank.transstats.models.TransactionSecondSummary
import com.bank.transstats.repositories.RecentTransactions
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertEquals
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

}