package com.kognity.betbotAPI.infra.persistense.repository

import com.kognity.betbotAPI.infra.persistense.entity.BetEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BetJpaRepository : JpaRepository<BetEntity, Long>
