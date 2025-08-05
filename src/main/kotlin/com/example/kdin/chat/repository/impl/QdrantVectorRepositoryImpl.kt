package com.example.kdin.chat.repository.impl

import com.example.kdin.chat.repository.VectorRepository
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Repository

@Repository
class QdrantVectorRepositoryImpl(
    private val vectorStore: VectorStore
) : VectorRepository {

    override fun saveDocument(document: List<Document>) {
        vectorStore.add(document)
    }

    override fun findDocuments(query: String): List<Document> {
        return vectorStore.similaritySearch(query)?.toList() ?: emptyList()
    }
}