package com.kognity.betbotAPI.domain.model

import java.time.LocalDateTime

data class Bet(
    val id: Long? = null,
    val userId: Long,
    val houseId: Long,
    val amount: Double,
    val odd: Double,
    val result: ResultBet,
    val date: LocalDateTime = LocalDateTime.now()
)

enum class ResultBet {
    GANHOU, PERDEU, PENDENTE
}