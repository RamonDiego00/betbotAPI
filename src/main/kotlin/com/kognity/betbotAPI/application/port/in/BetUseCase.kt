package com.kognity.betbotAPI.application.port.`in`

import com.kognity.betbotAPI.application.dto.BetResponse
import com.kognity.betbotAPI.application.dto.CreateBetRequest

interface BetUseCase {
    fun createBet(request:CreateBetRequest): BetResponse

    fun getBetById(id: Long): BetResponse?

    fun getAllBets(): List<BetResponse>

    fun updateBet(id: Long,request: CreateBetRequest): BetResponse?

    fun deleteBet(id: Long): Boolean
}