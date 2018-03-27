package com.bank.transstats.controllers

import com.bank.transstats.services.TransStatsService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class StatisticsController(val transStatsService: TransStatsService){

    @GetMapping("/statistics")
    fun getStatistics(): ResponseEntity<Any> {
        val statistics = transStatsService.getStatistics()
        return ResponseEntity.ok(statistics)
    }
}