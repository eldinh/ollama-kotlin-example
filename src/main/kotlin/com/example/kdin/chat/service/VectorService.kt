package com.example.kdin.chat.service

import com.example.kdin.chat.gateway.ParserGateway
import com.example.kdin.chat.repository.VectorRepository
import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class VectorService(
    private val vectorRepository: VectorRepository,
    private val parserGateway: ParserGateway,
) {

    fun retrieveFromSource(source: String): Mono<Unit> {
        return parserGateway.getParsedSiteBySource(source)
            .map {
                it.map { dto ->
                    Document
                        .builder()
                        .text(dto.text)
                        .metadata("metadata", mapOf(Pair("source", source)))
                        .build()
                }
            }
            .flatMap { Mono.defer { Mono.just(vectorRepository.saveDocument(it)).publishOn(Schedulers.parallel()) } }
    }

    fun getSimilarDocs(query: String): Mono<List<Document>> {
        return Mono.just(vectorRepository.findDocuments(query).sortedBy { it.score }.reversed())
            .doOnNext { log.info("Get similar docs ${it.map { doc -> doc.text }.toList()} for query $query") }
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}