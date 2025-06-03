package com.example.chatbot.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Executes a blocking API call within a coroutine context
 * using the provided dispatcher.
 * Allows invocation of blocking code safely
 * in a coroutine by shifting the execution to the specified dispatcher.
 *
 * @param dispatcher The coroutine dispatcher to shift
 *                   the blocking operation to. Defaults to [Dispatchers.IO].
 * @param blockingCall The lambda representing the blocking API call to execute.
 * @return The result of the blocking API call.
 */
suspend fun <T> callBlockingApi(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    blockingCall: () -> T,
): T = withContext(dispatcher) {
    blockingCall.invoke()
}
