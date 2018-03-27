package com.bank.transstats.repositories

import com.bank.transstats.models.TransactionSecondKey
import com.bank.transstats.models.TransactionSecondSummary
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

/** In memory repository */
@Repository
class RecentTransactions {

    /** Initialized with length 61 to ensure no automatic expansion occurs */
    private val store = ConcurrentHashMap<TransactionSecondKey, TransactionSecondSummary>(61, 1f)

    fun get(transactionSecondKey: TransactionSecondKey): TransactionSecondSummary?{
        return store.get(transactionSecondKey)
    }

    fun put(transactionSecondKey: TransactionSecondKey,
            transactionSecondSummary: TransactionSecondSummary){
        store.put(transactionSecondKey, transactionSecondSummary)
    }

    fun remove(transactionSecondKey: TransactionSecondKey) {
        store.remove(transactionSecondKey)
    }
}