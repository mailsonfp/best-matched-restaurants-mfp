package com.mailson.pereira.tech.assessment.input.metric.maintenance.dto

import java.math.BigDecimal

data class SearchParamsMetricDTO (
    val restaurantName: String?,
    val distance: Int?,
    val customerRating: Int?,
    val price: BigDecimal?,
    val cuisineName: String?,
    val searchDate: String? = null,
)