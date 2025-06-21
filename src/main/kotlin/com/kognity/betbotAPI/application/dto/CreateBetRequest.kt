package com.kognity.betbotAPI.application.dto

import com.kognity.betbotAPI.domain.model.ResultBet
import java.time.LocalDateTime

data class CreateBetRequest (
    val userId: Long,
    val houseId: Long,
    val amount: Double,
    val odd: Double,
    val result: ResultBet,
    val date: LocalDateTime = LocalDateTime.now()
)