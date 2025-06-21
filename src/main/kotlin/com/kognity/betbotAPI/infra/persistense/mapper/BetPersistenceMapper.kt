package com.kognity.betbotAPI.infra.persistense.mapper
import com.kognity.betbotAPI.domain.model.Bet
import com.kognity.betbotAPI.infra.persistense.entity.BetEntity
import org.springframework.stereotype.Component

@Component
class BetPersistenceMapper {
    fun toEntity(bet: Bet): BetEntity {
        return BetEntity(
            userId = bet.userId,
            houseId = bet.houseId,
            amount = bet.amount,
            odd = bet.odd,
            result = bet.result,
        )
    }

    fun toDomain(entity: BetEntity): Bet {
        return Bet(
            id = entity.id!!,
            userId = entity.userId,
            houseId = entity.houseId,
            amount = entity.amount,
            odd = entity.odd,
            result = entity.result,
        )
    }
}