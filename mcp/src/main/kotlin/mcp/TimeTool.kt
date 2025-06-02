@file:Suppress("unused")

package mcp

import io.quarkiverse.mcp.server.Tool
import io.quarkiverse.mcp.server.ToolArg
import io.quarkiverse.mcp.server.ToolResponse
import kotlinx.datetime.Clock
import kotlinx.datetime.IllegalTimeZoneException
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TimeTool {

    @Tool(description = "Returns current time at timezone")
    fun time(
        @ToolArg(description = "Timezone or empty for UTC", required = false)
        timezone: String? = "UTC"
    ): ToolResponse = try {
        val timeZone = if (timezone?.isNotBlank() == true) {
            TimeZone.of(timezone.trim())
        } else {
            TimeZone.UTC
        }
        ToolResponse.success(Clock.System.now().toLocalDateTime(timeZone).toString())
    } catch (_: IllegalTimeZoneException) {
        ToolResponse.error("Invalid timezone: '$timezone'")
    } catch (_: Exception) {
        ToolResponse.error(
            "An error occurred while processing your request. Please try again later."
        )
    }
}
