package com.example.chatbot.internal

import io.quarkus.mailer.Mail
import io.quarkus.mailer.Mailer
import kotlinx.coroutines.CoroutineDispatcher

suspend fun Mailer.sendSuspending(
    mail: Mail,
    dispatcher: CoroutineDispatcher,
) {
    val mailer = this
    callBlockingApi(dispatcher) {
        mailer.send(mail)
    }
}
