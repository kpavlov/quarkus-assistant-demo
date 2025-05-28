package com.example.chatbot.api

import com.example.chatbot.Answer
import com.example.chatbot.AssistantService
import com.example.chatbot.errorAnswer
import com.example.chatbot.greeting
import io.quarkus.logging.Log
import io.quarkus.websockets.next.OnError
import io.quarkus.websockets.next.OnOpen
import io.quarkus.websockets.next.OnTextMessage
import io.quarkus.websockets.next.WebSocket
import io.quarkus.websockets.next.WebSocketConnection
import kotlinx.datetime.FixedOffsetTimeZone
import kotlinx.datetime.UtcOffset

@WebSocket(path = "/chatbot")
@Suppress("unused")
class ChatBotWebSocket(
    private val assistantService: AssistantService,
) {
    @OnOpen
    fun onOpen(connection: WebSocketConnection): Answer = greeting

    @OnTextMessage
    suspend fun onMessage(
        request: ApiRequest,
    ): Answer {
        val userTimezone =
            FixedOffsetTimeZone(offset = UtcOffset(minutes = -request.timezoneOffset))
        val userInfo = mapOf("timeZone" to userTimezone.id)

        val answer = assistantService.askQuestion(
            memoryId = request.sessionId,
            question = request.message,
            userInfo = userInfo,
        )
        return answer
    }

    @OnError
    fun onError(
        connection: WebSocketConnection,
        error: Throwable,
    ): Answer {
        Log.error("Error while processing message", error)
        return errorAnswer
    }

}
