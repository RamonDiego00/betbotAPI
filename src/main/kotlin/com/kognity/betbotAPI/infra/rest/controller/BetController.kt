package com.kognity.betbotAPI.infra.rest.controller

import com.kognity.betbotAPI.application.dto.CreateBetRequest
import com.kognity.betbotAPI.application.port.`in`.BetUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/bet")
class BetController(
    private val betUseCase: BetUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBet(@RequestBody request: CreateBetRequest) =
        betUseCase.createBet(request)

    @GetMapping("/{id}")
    fun getBet(@PathVariable id: Long) =
        betUseCase.getBetById(id)

    @GetMapping
    fun getAllBets() =
        betUseCase.getAllBets()
}