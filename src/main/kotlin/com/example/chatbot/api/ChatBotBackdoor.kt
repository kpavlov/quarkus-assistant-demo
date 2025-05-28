package com.example.chatbot.api

import com.example.chatbot.Answer
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path

@Path("/chatbot/backdoor")
class ChatBotBackdoor(
    private val webSocketEndpoint: ChatBotWebSocket,
) {

    @POST
    suspend fun handleRequest(
        request: ApiRequest,
    ): Answer = webSocketEndpoint.onMessage(request)
}
