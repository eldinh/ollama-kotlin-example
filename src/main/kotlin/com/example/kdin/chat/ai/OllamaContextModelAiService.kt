package com.example.kdin.chat.ai

import com.example.kdin.chat.constant.ChatConstant.INSTRUCTION_LLM_PROMPT
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.spring.AiService
import dev.langchain4j.service.spring.AiServiceWiringMode
import reactor.core.publisher.Flux

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT, streamingChatModel = "ollamaContextModel")
interface OllamaContextModelAiService {

    @SystemMessage(INSTRUCTION_LLM_PROMPT)
    fun retriveAnswer(userMessage: String): Flux<String>

    fun splitIntoQuestions(userMessage: String): Flux<String>
}