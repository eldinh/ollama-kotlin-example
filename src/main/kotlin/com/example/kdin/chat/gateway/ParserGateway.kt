package com.example.kdin.chat.gateway

import com.example.kdin.chat.client.ParserClient
import com.example.kdin.chat.dto.ParserDto
import com.example.kdin.chat.exception.ParserIntegrationException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ParserGateway(
    private val parserClient: ParserClient,
) {

    fun getParsedSiteBySource(source: String): Mono<List<ParserDto>> {
        return parserClient.getParsedSite(source)
            .doOnNext { log.info("Success response from parser. Number of parsed text : ${it.size}") }
            .doOnError { log.error("Failed to integrate. Reason: ${it.message}", it) }
            .onErrorMap { ParserIntegrationException("Failed to integrate with parser. Reason ${it.message}") }
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}