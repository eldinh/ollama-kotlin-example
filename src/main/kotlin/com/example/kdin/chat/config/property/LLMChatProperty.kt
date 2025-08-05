package com.example.kdin.chat.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kdin.chat")
data class LLMChatProperty(
    val embeddingModel: String,
    val contextModel: String,
)
