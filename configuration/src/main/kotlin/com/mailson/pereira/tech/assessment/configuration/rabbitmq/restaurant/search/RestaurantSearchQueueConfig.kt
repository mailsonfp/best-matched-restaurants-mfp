package com.mailson.pereira.tech.assessment.configuration.rabbitmq.restaurant.search

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RestaurantSearchQueueConfig(
    @Value("\${rabbitmq.restaurant-search-queue}")
    private val searchQueueName: String,
    @Value("\${rabbitmq.restaurant-search-exchange}")
    private val searchExchangeName: String,
    @Value("\${rabbitmq.restaurant-search-queue-dlq}")
    private val searchDlqQueueName: String,
    @Value("\${rabbitmq.restaurant-search-exchange-dlq}")
    private val searchDlqExchange: String,
) {
    @Bean
    fun searchQueue(): Queue {
        return QueueBuilder.durable(searchQueueName)
            .withArgument("x-dead-letter-exchange", searchDlqExchange)
            .withArgument("x-dead-letter-routing-key", searchDlqQueueName)
            .build()
    }

    @Bean
    fun searchExchange(): FanoutExchange = ExchangeBuilder.fanoutExchange(searchExchangeName).build()

    @Bean
    fun searchBinding(): Binding =
        BindingBuilder.bind(searchQueue()).to(searchExchange())

    @Bean
    fun searchDlqQueue(): Queue = QueueBuilder.durable(searchDlqQueueName).build()

    @Bean
    fun searchDlqExchange(): FanoutExchange = ExchangeBuilder.fanoutExchange(searchDlqExchange).build()

    @Bean
    fun searchDlqBinding(): Binding =
        BindingBuilder.bind(searchDlqQueue()).to(searchDlqExchange())
}