package com.kognity.betbotAPI.application.service

import com.kognity.betbotAPI.application.dto.BetResponse
import com.kognity.betbotAPI.application.dto.CreateBetRequest
import com.kognity.betbotAPI.application.port.`in`.BetUseCase
import com.kognity.betbotAPI.application.port.out.BetPort
import com.kognity.betbotAPI.domain.model.Bet
import org.springframework.stereotype.Service


// Esse é nosso use case implementado que vai cuidar da chamada a camada de interfaces internas

@Service
class BetService(private val betPort: BetPort
) : BetUseCase {
    override fun createBet(request: CreateBetRequest): BetResponse {
        val bet = Bet(
            userId = request.userId,
            houseId = request.houseId,
            amount = request.amount,
            odd = request.odd,
            result = request.result,
        )

        val savedBet = betPort.save(bet)
        return savedBet.toResponse()
    }

    override fun getBetById(id: Long): BetResponse? {
        val bet = betPort.findById(id)
        return bet?.toResponse()
    }

    override fun getAllBets(): List<BetResponse> {
        return betPort.findAll().map { it.toResponse() }
    }

    override fun updateBet(id: Long, request: CreateBetRequest): BetResponse? {
        TODO("Not yet implemented")
    }

    override fun deleteBet(id: Long): Boolean {
        TODO("Not yet implemented")
    }


    private fun Bet.toResponse() = BetResponse(
        id = id!!,
        userId = userId,
        houseId = houseId,
        amount = amount,
        odd = odd,
        result = result,
    )


}