package com.example.kdin.chat.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kdin.chat")
data class LLMChatProperty(
    val embeddingModel: String,
    val contextModel: String,
    val ollama: Ollama,
    val qdrant: Qdrant,
) {
    data class Ollama(
        val url: String
    )

    data class Qdrant(
        val host: String,
        val port: Int,
        val collectionName : String
    )
}
