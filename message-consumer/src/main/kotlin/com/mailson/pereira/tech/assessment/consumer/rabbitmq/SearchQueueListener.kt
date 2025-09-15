package com.mailson.pereira.tech.assessment.consumer.rabbitmq

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload

class SearchQueueListener {
    @RabbitListener(queues = ["\${rabbitmq.restaurant-search-queue}"])
    private fun searchQueueListener(@Payload message: Any){
        println(message)
    }
}