package com.rmolcr.playground.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.beans.factory.annotation.Value

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiConfig(
    @Value("\${app.rest.api.name}")
    private val name: String,
    @Value("\${app.rest.api.description}")
    private val description: String,
    @Value("\${app.rest.api.version}")
    private val version: String
) {

    @Bean
    fun openAPI(): OpenAPI? {
        return OpenAPI()
                .info(Info().title(name)
                        .description(description)
                        .version(version)
                )
    }
}