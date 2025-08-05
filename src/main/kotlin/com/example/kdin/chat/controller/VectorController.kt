package com.example.kdin.chat.controller

import com.example.kdin.chat.service.VectorService
import org.springframework.ai.document.Document
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RequestMapping("/api/v1/vector")
@RestController
class VectorController(
    private val vectorService: VectorService
) {

    @PostMapping
    fun createDocumentFromSource(@RequestParam source: String): Mono<Unit> {
        return vectorService.retrieveFromSource(source)
    }

    @GetMapping
    fun getSimilarDocs(@RequestParam query: String): Mono<List<Document>> {
        return vectorService.getSimilarDocs(query)
    }
}