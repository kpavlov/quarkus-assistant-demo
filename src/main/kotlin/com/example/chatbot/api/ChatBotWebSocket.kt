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
import kotlinx.serialization.json.Json

@WebSocket(path = "/chatbot")
@Suppress("unused")
class ChatBotWebSocket(
    private val assistantService: AssistantService,
) {
    @OnOpen
    fun onOpen(connection: WebSocketConnection) {
        if (connection.handshakeRequest().query() == "greet") {
            // send greeting only to Web UI chat
            connection.sendTextAndAwait(Json.encodeToString(greeting))
        }
    }

    @OnTextMessage
    suspend fun onMessage(
        request: ApiRequest,
    ): Answer {
        val userTimezone =
            FixedOffsetTimeZone(UtcOffset(minutes = -request.timezoneOffset))
        val userInfo = mapOf("timeZone" to userTimezone.id)

        if (request.message.isBlank()) {
            return Answer(message = "Sorry, I didn't get that?")
        }
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
