package com.mailson.pereira.tech.assessment.input.metric.report

import com.mailson.pereira.tech.assessment.input.metric.report.dto.MetricReportResponseDTO

interface SearchMetricReportInput {
    fun getAverageReportData(
        periodType: String,
        initialPeriod: String,
        finalPeriod: String
    ): List<MetricReportResponseDTO>
    fun validateReportParams(
        periodType: String,
        initialPeriod: String,
        finalPeriod: String
    )
}