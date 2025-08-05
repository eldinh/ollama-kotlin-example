package com.example.kdin.chat.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Config {

    @Bean
    fun webclient(): WebClient {
        return WebClient.builder().build()
    }

    @Bean
    fun builder(): RestClient.Builder {
        return RestClient.builder().requestFactory(SimpleClientHttpRequestFactory())
    }
}