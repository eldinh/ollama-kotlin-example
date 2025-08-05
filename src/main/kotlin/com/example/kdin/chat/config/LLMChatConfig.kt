package com.example.kdin.chat.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class LLMChatConfig {

    @Bean
    fun chatClient(builder: ChatClient.Builder): ChatClient {
        return builder
            .defaultAdvisors(
                SimpleLoggerAdvisor(),  //simply logs requests and responses with a Model
            )
            .build()
    }
}