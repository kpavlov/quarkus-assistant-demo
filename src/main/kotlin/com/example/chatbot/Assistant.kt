@file:Suppress(
    "CdiManagedBeanInconsistencyInspection",
    "unused",
    "kotlin:S6517",
    "CdiUnproxyableBeanTypesInspection"
)

package com.example.chatbot

import com.example.chatbot.tools.CustomerCallbackScheduler
import com.example.chatbot.tools.MarketData
import com.example.chatbot.tools.McpToolProviderSupplier
import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.Moderate
import dev.langchain4j.service.ModerationException
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage
import dev.langchain4j.service.V
import io.quarkiverse.langchain4j.RegisterAiService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.context.SessionScoped
import org.eclipse.microprofile.faulttolerance.Timeout
import java.time.temporal.ChronoUnit

@RegisterAiService(
    toolProviderSupplier = McpToolProviderSupplier::class,
    tools = [
        MarketData::class,
        CustomerCallbackScheduler::class
    ],
    // no need to declare a retrieval augmentor here, it is automatically generated and discovered
)
@SessionScoped
@ApplicationScoped
interface Assistant {

    @SystemMessage(fromResource = "/prompts/assistant-system-prompt.md")
    @Moderate
    @Throws(ModerationException::class)
    @Timeout(value = 60, unit = ChronoUnit.SECONDS)
    @UserMessage("User said: ```{{question}}```. User info: {{userInfo}}.")
    fun chat(
        @MemoryId memoryId: ChatMemoryId,
        question: Question,
        @V("userInfo") userInfo: Map<String, Any>,
    ): Answer
}
