@file:Suppress("unused")

package mcp

import io.quarkiverse.mcp.server.Tool
import io.quarkiverse.mcp.server.ToolArg

class HelloTool {

    @Tool(description = "Returns a greeting for a given target")
    fun hello(
        @ToolArg(description = "The name or identifier to greet")
        who: String
    ): String = "Hello, $who"
}
