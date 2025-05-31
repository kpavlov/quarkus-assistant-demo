@file:Suppress("unused")

package mcp

import io.quarkiverse.mcp.server.Tool
import io.quarkiverse.mcp.server.ToolArg

class HelloTool {

    @Tool(name = "hello", description = "Says hello to someone")
    fun time(
        @ToolArg(description = "Who you want to be greeted")
        who: String
    ): String = "Hello, $who"
}
