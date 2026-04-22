package com.lilac.tools;

import jakarta.annotation.Resource;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工具注册
 */
@Configuration
public class ToolRegistration {

    @Resource
    private TerminateTool terminateTool;

    @Autowired(required = false)
    private ToolCallbackProvider toolCallbackProvider;

    @Bean
    public ToolCallback[] allTools() {
        ToolCallback[] localTools = ToolCallbacks.from(terminateTool);

        if (toolCallbackProvider != null) {
            ToolCallback[] mcpTools = toolCallbackProvider.getToolCallbacks();
            ToolCallback[] allTools = new ToolCallback[localTools.length + mcpTools.length];
            System.arraycopy(localTools, 0, allTools, 0, localTools.length);
            System.arraycopy(mcpTools, 0, allTools, localTools.length, mcpTools.length);
            return allTools;
        }
        return localTools;
    }
}
