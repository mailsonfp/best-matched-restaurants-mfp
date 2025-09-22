package com.mailson.pereira.tech.assessment.consumer.rabbitmq

import com.mailson.pereira.tech.assessment.input.message.consumer.SearchMetricProcessService
import com.mailson.pereira.tech.assessment.input.message.consumer.dto.MessageInputDTO
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class SearchMetricQueueListener(
    private val searchMetricProcessService: SearchMetricProcessService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["\${rabbitmq.restaurant-search-queue}"])
    private fun searchQueueListener(@Payload message: MessageInputDTO){
        try {
            searchMetricProcessService.processSearchMetric(message)
        } catch (ex:Exception) {
            logger.error("method=searchQueueListener, status=error message=${ex.message}", ex)
        }
    }
}