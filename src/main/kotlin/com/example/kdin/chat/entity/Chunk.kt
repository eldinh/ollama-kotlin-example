package com.example.kdin.chat.entity

data class Chunk(
    val text: String,
    val score: Double? = null,
    val metadata: Map<String, Any>?
)
