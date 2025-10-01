package com.mailson.pereira.tech.assessment.output.metric

import com.mailson.pereira.tech.assessment.entities.enums.SummarizeDataTypeEnum
import com.mailson.pereira.tech.assessment.output.metric.projection.AverageDataProjectionDTO
import com.mailson.pereira.tech.assessment.output.metric.projection.AverageMetricDetailDataProjectionDTO
import com.mailson.pereira.tech.assessment.output.metric.projection.TopParamKeyAndValueMetricDetailProjectionDTO
import com.mailson.pereira.tech.assessment.output.metric.projection.TopParamKeyMetricDetailProjectionDTO
import java.time.LocalDateTime

interface SearchMetricReportRepository {
    fun summarizeByPeriod(
        type: SummarizeDataTypeEnum,
        start: String,
        end: String
    ): List<AverageDataProjectionDTO>

    fun averageNumericParamsByPeriod(
        type: SummarizeDataTypeEnum,
        start: String,
        end: String
    ): List<AverageMetricDetailDataProjectionDTO>

    fun topParamKeyByPeriod(
        type: SummarizeDataTypeEnum,
        start: String,
        end: String
    ): List<TopParamKeyMetricDetailProjectionDTO>

    fun topParamKeyValueByPeriod(
        type: SummarizeDataTypeEnum,
        start: String,
        end: String
    ): List<TopParamKeyAndValueMetricDetailProjectionDTO>
}