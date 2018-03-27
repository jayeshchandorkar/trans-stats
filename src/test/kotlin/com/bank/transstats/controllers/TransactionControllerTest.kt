package com.bank.transstats.controllers

import com.bank.transstats.models.TransactionRequest
import com.bank.transstats.services.TransStatsService
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TransactionControllerTest: BaseTestController<TransactionController>(){

    @InjectMocks
    override lateinit  var controller: TransactionController

    @Mock
    private lateinit var transStatsService: TransStatsService

    @Test
    fun save_olderThanMinuteTransaction_returnsNoContent(){
        val amount = 12.3
        val oldTimestamp = 1522176269569

        this.mockMvc.perform(post("/transactions")
                .content(
                        """{
                            "amount": ${amount},
                            "timestamp": ${oldTimestamp}
                        }"""
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
                .andExpect( content().string("") )
    }

    @Test
    fun save_futureTransaction_returnsNoContent(){
        val amount = 12.3
        val futureTimestamp = System.currentTimeMillis() + 60 * 1000

        this.mockMvc.perform(post("/transactions")
                .content(
                        """{
                            "amount": ${amount},
                            "timestamp": ${futureTimestamp}
                        }"""
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
                .andExpect( content().string("") )
    }

    @Test
    fun save_validTransaction_returnsCreated(){
        val amount = 12.3
        val timestamp = System.currentTimeMillis() - 5

        this.mockMvc.perform(post("/transactions")
                .content(
                        """{
                            "amount": ${amount},
                            "timestamp": ${timestamp}
                        }"""
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect( content().string("") )

        verify(transStatsService).saveTransaction(TransactionRequest(amount, timestamp))
    }

}