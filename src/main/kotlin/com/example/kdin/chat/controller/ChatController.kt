package com.example.kdin.chat.controller

import com.example.kdin.chat.dto.ChatChunkResponse
import com.example.kdin.chat.dto.UserPrompt
import com.example.kdin.chat.service.ChatService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@CrossOrigin(origins = ["http://localhost:63342"], allowCredentials = "true", allowedHeaders = ["*"])
@RestController
@RequestMapping("/api/v1/chat")
class ChatController(
    private val chatService: ChatService
) {

    @PostMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun handleUserPrompt(@RequestBody userPrompt: UserPrompt): Flux<ChatChunkResponse> {
        return chatService.getAnswer(userPrompt).map { ChatChunkResponse(it) }
    }


    @PostMapping("/text")
    fun splitUserPrompt(@RequestBody userPrompt: UserPrompt): Mono<List<String>> {
        return chatService.splitIntoSemanticQuestions(userPrompt.prompt)
    }
}