package com.example.kdin.chat.config

import com.example.kdin.chat.config.property.LLMChatProperty
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.model.ollama.OllamaEmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class QdrantConfig(
    private val llmChatProperty: LLMChatProperty
) {

    @Bean
    fun qdrantEmbeddingStore(): EmbeddingStore<TextSegment> {
        return QdrantEmbeddingStore.builder()
            .collectionName(llmChatProperty.qdrant.collectionName)
            .host(llmChatProperty.qdrant.host)
            .port(llmChatProperty.qdrant.port)
            .build();
    }

    @Bean
    fun embeddingModel(): EmbeddingModel {
        return OllamaEmbeddingModel.builder()
            .baseUrl(llmChatProperty.ollama.url)
            .modelName(llmChatProperty.embeddingModel)
            .logRequests(true)
            .logResponses(true)
            .build()
    }
}