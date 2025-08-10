package com.example.kdin.chat.repository

import com.example.kdin.chat.entity.Chunk

interface VectorRepository {

    fun saveDocument(document: List<Chunk>)

    fun findDocuments(query: String): List<Chunk>
}