package com.mailson.pereira.tech.assessment.output.metric.projection

data class TopParamKeyAndValueMetricDetailProjectionDTO(
    val period: String,
    val paramKey: String,
    val paramValue: String?,
    val total: Long
)
