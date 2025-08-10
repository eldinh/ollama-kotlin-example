package com.example.kdin.chat.adapter

import com.example.kdin.chat.ai.OllamaContextModelAiService
import com.example.kdin.chat.constant.ChatConstant.CONTEXT_LLM_SYSTEM_PROMPT
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Component
class OllamaChatAdapter(
    private val ollamaContextModelAiService: OllamaContextModelAiService,
) {

    fun retrieveAnswerAsChunkFromChatModel(userPrompt: String): Flux<String> {
        log.info("User prompt -> $userPrompt")
        return ollamaContextModelAiService.retriveAnswer(userPrompt);
    }

    fun sendPromptForSemanticParse(userPrompt: String): Mono<List<String>> {
        return ollamaContextModelAiService.splitIntoQuestions(CONTEXT_LLM_SYSTEM_PROMPT.format(userPrompt))
            .reduce("") { acc, resp -> acc.plus(resp ?: "") }
            .map { it.split('\n').filter { text -> text.trim().isNotEmpty() } }
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(this::class.java)
    }

}