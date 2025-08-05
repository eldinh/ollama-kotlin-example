package com.example.kdin.chat.adapter

import com.example.kdin.chat.config.property.LLMChatProperty
import com.example.kdin.chat.constant.ChatConstant.CONTEXT_LLM_SYSTEM_PROMPT
import com.example.kdin.chat.constant.ChatConstant.INSTRUCTION_LLM_PROMPT
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.ollama.api.OllamaOptions
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Component
class OllamaChatAdapter(
    private val chatModel: OllamaChatModel,
    private val llmChatProperty: LLMChatProperty
) {

    fun retrieveAnswerAsChunkFromChatModel(userPrompt: String): Flux<String> {
        log.info("User prompt -> $userPrompt")
        return chatModel.stream(
            Prompt.builder()
                .messages(
                    SystemMessage.builder()
                        .text(INSTRUCTION_LLM_PROMPT)
                        .build(),
                    UserMessage.builder()
                        .text(userPrompt)
                        .build()
                )
                .chatOptions(
                    OllamaOptions.builder()
                        .model(llmChatProperty.contextModel)
                        .build()
                )
                .build()
        ).mapNotNull { it.result?.output?.text }
    }

    fun sendPromptForSemanticParse(userPrompt: String): Mono<List<String>> {
        return chatModel.stream(
            Prompt.builder()
                .messages(
                    UserMessage.builder()
                        .text(CONTEXT_LLM_SYSTEM_PROMPT.format(userPrompt))
                        .build()
                )
                .chatOptions(
                    OllamaOptions.builder()
                        .model(llmChatProperty.contextModel)
                        .build()
                )
                .build()
        ).reduce("") { acc, resp -> acc.plus(resp.result?.output?.text ?: "") }
            .map { it.split('\n').filter { text -> text.trim().isNotEmpty() } }
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(this::class.java)
    }

}