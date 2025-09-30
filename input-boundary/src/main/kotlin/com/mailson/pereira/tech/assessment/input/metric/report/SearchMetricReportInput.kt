package com.mailson.pereira.tech.assessment.input.metric.report

import com.mailson.pereira.tech.assessment.input.metric.report.dto.AverageDataResponseDTO

interface SearchMetricReportInput {
    fun getAverageReportData(
        periodType: String,
        initialPeriod: String,
        finalPeriod: String
    ): List<AverageDataResponseDTO>
    fun validateReportParams(
        periodType: String,
        initialPeriod: String,
        finalPeriod: String
    )
}