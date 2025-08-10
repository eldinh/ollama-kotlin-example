package com.example.kdin.chat.repository.impl

import com.example.kdin.chat.entity.Chunk
import com.example.kdin.chat.repository.VectorRepository
import dev.langchain4j.data.document.Metadata
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingSearchRequest
import dev.langchain4j.store.embedding.EmbeddingStore
import org.springframework.stereotype.Repository

@Repository
class QdrantVectorRepositoryImpl(
    private val embeddingStore: EmbeddingStore<TextSegment>,
    private val embeddingModel: EmbeddingModel,
) : VectorRepository {

    override fun saveDocument(document: List<Chunk>) {
        document.map { TextSegment.from(it.text, Metadata(it.metadata)) }
            .forEach { embeddingStore.add(embeddingModel.embed(it).content(), it) }
    }

    override fun findDocuments(query: String): List<Chunk> {
        return embeddingStore.search(
            EmbeddingSearchRequest.builder()
                .queryEmbedding(embeddingModel.embed(query).content())
                .build()
        )?.let {
            it.matches().map { segment ->
                Chunk(
                    text = segment.embedded()?.text() ?: "",
                    metadata = segment.embedded()?.metadata()?.toMap() ?: emptyMap(),
                    score = segment.score()
                )
            }
        } ?: emptyList()
    }
}