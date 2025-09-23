package com.mailson.pereira.tech.assessment.output.metric

import com.mailson.pereira.tech.assessment.entities.enums.SummarizeDataTypeEnum
import com.mailson.pereira.tech.assessment.output.metric.projection.AverageDataProjectionDTO
import java.time.LocalDateTime

interface SearchMetricReportRepository {
    fun summarizeByPeriod(
        type: SummarizeDataTypeEnum,
        start: LocalDateTime,
        end: LocalDateTime
    ): List<AverageDataProjectionDTO>
}