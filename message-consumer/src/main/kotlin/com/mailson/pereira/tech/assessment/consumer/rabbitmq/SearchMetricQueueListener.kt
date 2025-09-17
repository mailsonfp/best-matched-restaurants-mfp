package com.mailson.pereira.tech.assessment.consumer.rabbitmq

import com.mailson.pereira.tech.assessment.input.message.consumer.dto.MessageInputDTO
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class SearchMetricQueueListener {
    @RabbitListener(queues = ["\${rabbitmq.restaurant-search-queue}"])
    private fun searchQueueListener(@Payload message: MessageInputDTO){
        println(message)
    }
}