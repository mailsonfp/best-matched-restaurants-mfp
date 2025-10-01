package com.mailson.pereira.tech.assessment.input.metric.report.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

data class MetricReportResponseDTO(
    val period: String,
    val searchMetricSummaryInformation: MetricSearchDataResponseDTO? = null,
    val searchMetricDetailSummaryInformation: MetricSearchDetailDataResponseDTO? = null
)

data class MetricSearchDataResponseDTO (
    val totalSearch: Long,
    val withResultMatched: Long,
    val withoutResultMatched: Long
)

data class MetricSearchDetailDataResponseDTO(
    val numericDetailParamsAverageInformation: List<MetricSearchDetailItemDataResponseDTO>,
    val topParamKeySearchedInformation: List<MetricSearchDetailItemDataResponseDTO>,
    val topParamKeyAndParamValueSearchedInformation: List<MetricSearchDetailItemDataResponseDTO>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MetricSearchDetailItemDataResponseDTO(
    val paramKey: String? = null,
    val paramValue: String? = null,
    val average: BigDecimal? = null,
    val total: Long? = null
)