package com.mailson.pereira.tech.assessment.configuration.openai

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition
class OpenAPIConfiguration {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Mailson Fernando Pereira - Tech assessment")
                    .version("v1")
                    .description("Project for Tech assessment")
            )
    }
}