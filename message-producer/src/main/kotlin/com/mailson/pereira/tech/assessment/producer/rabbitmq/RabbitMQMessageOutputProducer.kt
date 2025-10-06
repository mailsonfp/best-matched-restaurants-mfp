package com.mailson.pereira.tech.assessment.producer.rabbitmq

import com.mailson.pereira.tech.assessment.output.message.producer.MessageOutputProducer
import com.mailson.pereira.tech.assessment.output.message.producer.dto.MessageOutputDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RabbitMQMessageOutputProducer(
    private val rabbitTemplate: RabbitTemplate,
    @Value("\${rabbitmq.restaurant-search-exchange}")
    private val searchExchangeName: String,
): MessageOutputProducer {

    override fun sendMessageToSearchQueue(message: MessageOutputDTO) {
        rabbitTemplate.convertAndSend(searchExchangeName, "", message)
    }
}