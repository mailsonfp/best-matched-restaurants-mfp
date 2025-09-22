package com.mailson.pereira.tech.assessment.output.metric

import com.mailson.pereira.tech.assessment.output.metric.dto.SearchMetricDetailOutputDTO

interface SearchMetricDetailRepository {
    fun save(searchMetricDetailOutputDTO: SearchMetricDetailOutputDTO): SearchMetricDetailOutputDTO
    fun saveList(searchMetricDetailList: List<SearchMetricDetailOutputDTO>): List<SearchMetricDetailOutputDTO>
}