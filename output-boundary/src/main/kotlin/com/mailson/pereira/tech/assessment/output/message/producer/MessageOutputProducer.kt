package com.mailson.pereira.tech.assessment.output.message.producer

import com.mailson.pereira.tech.assessment.output.message.producer.dto.MessageOutputDTO

interface MessageOutputProducer {
    fun sendMessageToSearchQueue(message: MessageOutputDTO)
}