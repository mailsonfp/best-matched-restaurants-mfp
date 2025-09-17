package com.mailson.pereira.tech.assessment.service.search.metric.process

import com.mailson.pereira.tech.assessment.input.message.consumer.SearchMetricProcessService
import com.mailson.pereira.tech.assessment.input.message.consumer.dto.MessageInputDTO
import com.mailson.pereira.tech.assessment.output.metric.SearchMetricDetailRepository
import com.mailson.pereira.tech.assessment.output.metric.SearchMetricRepository

class SearchMetricProcessServiceImpl(
    private val searchMetricRepository: SearchMetricRepository,
    private val searchMetricDetailRepository: SearchMetricDetailRepository
):SearchMetricProcessService {
    override fun processSearchMetric(searchMetricMessage: MessageInputDTO) {
        TODO("Not yet implemented")
    }
}