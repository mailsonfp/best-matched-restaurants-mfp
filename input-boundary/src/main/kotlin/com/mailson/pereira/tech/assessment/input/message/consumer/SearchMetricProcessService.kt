package com.mailson.pereira.tech.assessment.input.message.consumer

import com.mailson.pereira.tech.assessment.input.message.consumer.dto.MessageInputDTO

interface SearchMetricProcessService {
    fun processSearchMetric(searchMetricMessage: MessageInputDTO)
}