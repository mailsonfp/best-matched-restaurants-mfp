package com.mailson.pereira.tech.assessment.input.message.consumer.metric

import com.mailson.pereira.tech.assessment.input.message.consumer.metric.dto.MessageInputDTO

interface SearchMetricProcessService {
    fun processSearchMetric(searchMetricMessage: MessageInputDTO)
}