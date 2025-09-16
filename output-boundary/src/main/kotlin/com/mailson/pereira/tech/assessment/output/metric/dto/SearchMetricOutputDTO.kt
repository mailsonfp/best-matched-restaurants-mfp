package com.mailson.pereira.tech.assessment.output.metric.dto

import java.time.LocalDateTime

data class SearchMetricOutputDTO(
    val id: Long ? = null,
    val searchDateTime: LocalDateTime = LocalDateTime.now(),
    val clientIp: String,
    val userAgent: String? = null,
    val referrer: String? = null,
    val resultCount: Int = 0,
    val otherMetadata: String? = null,
)
