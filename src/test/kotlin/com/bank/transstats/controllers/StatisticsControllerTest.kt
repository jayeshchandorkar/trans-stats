package com.bank.transstats.controllers

import com.bank.transstats.models.Statistics
import com.bank.transstats.services.TransStatsService
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class StatisticsControllerTest: BaseTestController<StatisticsController>(){

    @InjectMocks
    override lateinit var controller: StatisticsController

    @Mock
    lateinit var transStatService: TransStatsService

    @Test
    fun getStatistics_returnsOkWithStatistics(){
        given(transStatService.getStatistics()).willReturn(Statistics(1000.0, 100.0, 200.0, 50.0, 10 ))
        this.mockMvc.perform(MockMvcRequestBuilders.get("/statistics"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect( MockMvcResultMatchers.content().json(
                        """{
                                "sum": 1000,
                                "avg": 100,
                                "max": 200,
                                "min": 50,
                                "count": 10
                            }""".trimMargin()) )

        verify(transStatService).getStatistics()
    }
}