package com.mailson.pereira.tech.assessment.output.metric

import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricOutputDTO

interface SearchMetricRepository {
    fun save(searchMetricOutputDTO: SearchMetricOutputDTO): SearchMetricOutputDTO
}