package com.bank.transstats.models

data class TransactionSecondSummary(var count: Int=0,
                                    var sum: Double=0.0,
                                    var max: Double= Double.NEGATIVE_INFINITY,
                                    var min: Double= Double.POSITIVE_INFINITY
)