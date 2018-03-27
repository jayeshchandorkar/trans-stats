package com.bank.transstats.controllers

import org.junit.Test
import org.mockito.InjectMocks
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TransactionControllerTest: BaseTestController<TransactionController>(){

    @InjectMocks
    override lateinit  var controller: TransactionController

    @Test
    fun save_olderThanMinuteTransaction_returnsNoContent(){
        val amount: Double = 12.3
        val oldTimestamp: Long = 1522176269569

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

}