package com.kognity.betbotAPI.domain.model

data class ConfigsUser(
    val receiveAlerts: Boolean = true,
    val alertsByEmail: Boolean = true,
    val alertsByPush: Boolean = false
)