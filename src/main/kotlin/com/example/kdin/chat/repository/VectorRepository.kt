package com.example.kdin.chat.repository

import org.springframework.ai.document.Document

interface VectorRepository {

    fun saveDocument(document: List<Document>)

    fun findDocuments(query: String): List<Document>
}