package com.kognity.betbotAPI.infra.adapter.out


import com.kognity.betbotAPI.application.port.out.BetPort
import com.kognity.betbotAPI.domain.model.Bet
import com.kognity.betbotAPI.infra.persistense.mapper.BetPersistenceMapper
import com.kognity.betbotAPI.infra.persistense.repository.BetJpaRepository
import org.springframework.stereotype.Component

@Component
class PersistenceBetAdapter(
    private val betJpaRepository: BetJpaRepository,
    private val mapper: BetPersistenceMapper
) : BetPort {

    override fun save(bet: Bet): Bet {
        val entity = mapper.toEntity(bet)
        val savedEntity = betJpaRepository.save(entity)
        return mapper.toDomain(savedEntity)
    }

    override fun findById(id: Long): Bet? {
        return betJpaRepository.findById(id)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun findAll(): List<Bet> {
        return betJpaRepository.findAll()
            .map(mapper::toDomain)
    }
}