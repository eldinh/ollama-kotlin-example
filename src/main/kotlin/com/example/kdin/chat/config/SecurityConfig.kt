package com.example.kdin.chat.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun securityFilterCHain(http: ServerHttpSecurity) : SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .cors { it.disable() }
            .httpBasic { it.disable() }
            .authorizeExchange { it.anyExchange().permitAll() }
            .build()
    }

}