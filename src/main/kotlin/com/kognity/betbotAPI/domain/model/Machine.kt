package com.kognity.betbotAPI.domain.model

data class Machine (
    val id: Long? = null,
    val name: String,
    val status: StatusMachine = StatusMachine.ACTIVE,
)

enum class StatusMachine{
    ACTIVE,
    INACTIVE,
    MAINTENANCE,
    DECOMMISSIONED
}