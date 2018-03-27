package com.bank.transstats.controllers

import com.bank.transstats.models.TransactionRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class TransactionController{

    @PostMapping("/transactions")
    fun saveTransaction(@RequestBody transactionRequest: TransactionRequest): ResponseEntity<String> {
        if(transactionRequest.timestamp <= (System.currentTimeMillis() - 60000)){
            return ResponseEntity<String>("", null, HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<String>("", null, HttpStatus.CREATED)
    }
}