package com.example.chatbot.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    val message: String,
    val sessionId: String,
    val timezoneOffset: Int,
)
