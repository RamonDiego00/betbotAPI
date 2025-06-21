package com.kognity.betbotAPI.domain.repository

import com.kognity.betbotAPI.domain.model.Bet

interface BetRepository {
    fun findAll(): List<Bet>
    fun findById(id: Long): Bet?
    fun save(bet: Bet): Bet
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}