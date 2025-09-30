package com.mailson.pereira.tech.assessment.input.metric.report.dto

data class AverageDataResponseDTO(
    val period: String,
    val searchSummaryInformation: SearchDataResponseDTO
)

data class SearchDataResponseDTO (
    val totalSearch: Long,
    val withResultMatched: Long,
    val withoutResultMatched: Long
)
