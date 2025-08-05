package com.example.kdin.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ChatApplication

fun main(args: Array<String>) {
    runApplication<ChatApplication>(*args)
}
