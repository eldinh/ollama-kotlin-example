package com.example.kdin.chat.config

import com.example.kdin.chat.config.property.LLMChatProperty
import dev.langchain4j.model.chat.StreamingChatModel
import dev.langchain4j.model.ollama.OllamaStreamingChatModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration


@Configuration
class LLMChatConfig(
    private val llmChatProperty: LLMChatProperty,
) {

    @Bean
    fun ollamaContextModel(): StreamingChatModel {
        return OllamaStreamingChatModel.builder()
            .baseUrl(llmChatProperty.ollama.url)
            .temperature(0.0)
            .logRequests(true)
            .logResponses(true)
            .timeout(Duration.ofSeconds(60))
            .modelName(llmChatProperty.contextModel)
            .build()
    }
}