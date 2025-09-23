package com.mailson.pereira.tech.assessment.output.metric.projection

data class AverageDataProjectionDTO (
    val period: String,
    val total: Long,
    val withResult: Long,
    val withoutResult: Long
)