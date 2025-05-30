@file:Suppress("unused")

package mcp

import io.quarkiverse.mcp.server.Tool
import io.quarkiverse.mcp.server.ToolArg
import kotlinx.datetime.Clock
import kotlinx.datetime.IllegalTimeZoneException
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TimeTool {

    @Tool(name = "time", description = "Returns current time at timezone")
    fun time(
        @ToolArg(description = "Timezone or empty for UTC", required = false)
        timezone: String? = "UTC"
    ): String =
        try {
            val timeZone =  if (timezone?.isNotBlank() == true) {
                TimeZone.of(timezone)
            } else {
                TimeZone.UTC
            }
            Clock.System.now().toLocalDateTime(timeZone).toString()
        } catch (_: IllegalTimeZoneException) {
            "Invalid timezone: '$timezone'"
        }

}
