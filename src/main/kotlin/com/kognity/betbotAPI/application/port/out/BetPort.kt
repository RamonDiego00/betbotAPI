package com.kognity.betbotAPI.application.port.out

import com.kognity.betbotAPI.domain.model.Bet

interface BetPort {
    fun save(bet: Bet): Bet
    fun findById(id: Long): Bet?
    fun findAll(): List<Bet>
}