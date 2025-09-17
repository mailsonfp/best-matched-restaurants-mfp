package com.mailson.pereira.tech.assessment.configuration.rabbitmq

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig(
    @Value("\${spring.rabbitmq.host}")
    private val rabbitmqHost: String,
    @Value("\${spring.rabbitmq.port}")
    private val rabbitmqPort: Int,
    @Value("\${spring.rabbitmq.username}")
    private val rabbitmqUsername: String,
    @Value("\${spring.rabbitmq.password}")
    private val rabbitmqPassword: String
) {
    @Bean
    fun connectionFactory(): CachingConnectionFactory {
        return CachingConnectionFactory(rabbitmqHost, rabbitmqPort).apply {
            username = rabbitmqUsername
            setPassword(rabbitmqPassword)
        }
    }

    @Bean
    fun rabbitAdmin(connectionFactory: CachingConnectionFactory): RabbitAdmin {
        return RabbitAdmin(connectionFactory).apply {
            isAutoStartup = true
        }
    }

    @Bean
    fun jacksonMessageConverter(): Jackson2JsonMessageConverter {
        val objectMapper = ObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule()) // Suporte a LocalDateTime
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun rabbitTemplate(
        connectionFactory: CachingConnectionFactory,
        messageConverter: Jackson2JsonMessageConverter
    ): RabbitTemplate =
        RabbitTemplate(connectionFactory).apply {
            this.messageConverter = messageConverter
        }


    @Bean
    fun rabbitmqInitialize(rabbitAdmin: RabbitAdmin)= CommandLineRunner {
        rabbitAdmin.initialize()
    }
}