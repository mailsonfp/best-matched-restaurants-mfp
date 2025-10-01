package com.mailson.pereira.tech.assessment.output.metric.projection

import java.math.BigDecimal

data class AverageMetricDetailDataProjectionDTO(
    val period: String,
    val paramKey: String,
    val average: BigDecimal?
)
