package com.example.kdin.chat.client

import com.example.kdin.chat.dto.ParserDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class ParserClient(
    private val webClient: WebClient
) {

    fun getParsedSite(source: String): Mono<List<ParserDto>> {
        return webClient.get()
            .uri("http://localhost:5000/api/v1/parse") { it.queryParam("source", source).build() }
            .exchangeToMono { it.bodyToMono(object : ParameterizedTypeReference<List<ParserDto>>() {}) }
    }
}