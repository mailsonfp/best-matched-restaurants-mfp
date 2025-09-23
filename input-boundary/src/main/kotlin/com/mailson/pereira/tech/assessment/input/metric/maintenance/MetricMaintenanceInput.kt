package com.mailson.pereira.tech.assessment.input.metric.maintenance

import com.mailson.pereira.tech.assessment.input.metric.maintenance.dto.SearchParamsMetricDTO
import jakarta.servlet.http.HttpServletRequest

interface MetricMaintenanceInput {
    fun generateMetricDataByAPI(
        searchParamsMetricDTO: SearchParamsMetricDTO,
        httpServletRequest: HttpServletRequest
    )
}