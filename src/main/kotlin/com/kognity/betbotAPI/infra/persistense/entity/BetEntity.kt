package com.kognity.betbotAPI.infra.persistense.entity

import com.kognity.betbotAPI.domain.model.ResultBet
import jakarta.persistence.*

@Entity
@Table(name = "products")
data class BetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val houseId: Long,

    @Column(nullable = false)
    val amount: Double,

    @Column(nullable = false)
    val odd: Double,

    @Column(nullable = false)
    val result: ResultBet

)