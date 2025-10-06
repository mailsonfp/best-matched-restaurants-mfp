package com.mailson.pereira.tech.assessment.repository.jpa.specification

import com.mailson.pereira.tech.assessment.entities.enums.SummarizeDataTypeEnum
import com.mailson.pereira.tech.assessment.repository.domain.SearchMetric
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import java.time.LocalDateTime

object SearchMetricSpecification {
    fun groupByPeriod(summarizePeriodType: SummarizeDataTypeEnum): (CriteriaBuilder, Root<SearchMetric>) -> Expression<String> {
        return when (summarizePeriodType) {
            SummarizeDataTypeEnum.DAY -> { cb, root ->
                cb.function("DATE", String::class.java, root.get<LocalDateTime>("searchTimestamp")) as Expression<String>
            }
            SummarizeDataTypeEnum.MONTH -> { cb, root ->
                cb.function(
                    "TO_CHAR",
                    String::class.java,
                    root.get<LocalDateTime>("searchTimestamp"),
                    cb.literal("YYYY-MM")
                ) as Expression<String>
            }
            SummarizeDataTypeEnum.YEAR -> { cb, root ->
                cb.function(
                    "TO_CHAR",
                    String::class.java,
                    root.get<LocalDateTime>("searchTimestamp"),
                    cb.literal("YYYY")
                ) as Expression<String>
            }
            else -> throw IllegalArgumentException("Invalid grouping type: ${summarizePeriodType.name}")
        }
    }
}
