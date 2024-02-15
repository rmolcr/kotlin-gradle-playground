package com.rmolcr.playground.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate

@Configuration
class BreachRestClientConfig(
        @Value("\${app.breaches.api.baseUrl}")
        private val baseUrl : String
) {

    @Bean("breachRestTemplate")
    fun breachRestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder
            .rootUri(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }

}