package com.example.kdin.chat.service

import com.example.kdin.chat.adapter.OllamaChatAdapter
import com.example.kdin.chat.constant.ChatConstant.SUMMARIZING_LLM_SYSTEM_PROMPT
import com.example.kdin.chat.dto.UserPrompt
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ChatService(
    private val chatAdapter: OllamaChatAdapter,
    private val vectorService: VectorService,
) {

    fun getAnswer(userPrompt: UserPrompt): Flux<String> {
        return splitIntoSemanticQuestions(userPrompt.prompt)
            .flatMap { questions ->
                Flux.fromIterable(
                    questions.map { question ->
                        vectorService.getSimilarDocs(question)
                            .map {
                                it.mapNotNull { doc -> doc.text }
                                    .reduce { acc, document -> acc + "\n" + document }
                            }
                    }).flatMap { x -> x }.reduce { t, u -> t.plus(u) }
            }.flatMapMany { text ->
                chatAdapter.retrieveAnswerAsChunkFromChatModel(
                    SUMMARIZING_LLM_SYSTEM_PROMPT.format(userPrompt.prompt, text)
                )
            }
    }

    fun splitIntoSemanticQuestions(userPrompt: String): Mono<List<String>> {
        return chatAdapter.sendPromptForSemanticParse(userPrompt)
            .doOnNext { log.info("Text split into questions: $it") }
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}