package com.bank.transstats.models

data class TransactionSecondKey(val seconds:Long){

    // To achieve constant fetch time ensuring transactions are equally distributed in hash collections
    override fun hashCode():Int {
        return seconds.toInt()
    }
}