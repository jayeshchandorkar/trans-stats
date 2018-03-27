package com.bank.transstats.controllers

import com.bank.transstats.models.TransactionRequest
import com.bank.transstats.services.TransStatsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class TransactionController(val transStatsService: TransStatsService){

    @PostMapping("/transactions")
    fun saveTransaction(@RequestBody transactionRequest: TransactionRequest): ResponseEntity<String> {
        if(transactionRequest.timestamp <= (System.currentTimeMillis() - 60000)
                || transactionRequest.timestamp > System.currentTimeMillis()){
            return ResponseEntity<String>("", null, HttpStatus.NO_CONTENT)
        }
        transStatsService.saveTransaction(transactionRequest)
        return ResponseEntity<String>("", null, HttpStatus.CREATED)
    }
}