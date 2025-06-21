package com.kognity.betbotAPI.domain.model

data class User(
    val id: Long? = null,
    val name: String,
    val email: String,
    val houses: List<Houses> = emptyList(),
    val machines: List<Machine> = emptyList(),
    val configs: ConfigsUser = ConfigsUser(),
)
