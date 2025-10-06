package com.mailson.pereira.tech.assessment.repository.jpa.specification

import com.mailson.pereira.tech.assessment.entities.enums.SummarizeDataTypeEnum
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Path
import java.time.LocalDateTime

object SearchMetricDetailSpecification {
    fun groupByPeriod(type: SummarizeDataTypeEnum): (CriteriaBuilder, Path<LocalDateTime>) -> Expression<String> {
        return when (type) {
            SummarizeDataTypeEnum.DAY -> { cb, path ->
                cb.function("TO_CHAR", String::class.java, path, cb.literal("YYYY-MM-DD")) as Expression<String>
            }
            SummarizeDataTypeEnum.MONTH -> { cb, path ->
                cb.function("TO_CHAR", String::class.java, path, cb.literal("YYYY-MM")) as Expression<String>
            }
            SummarizeDataTypeEnum.YEAR -> { cb, path ->
                cb.function("TO_CHAR", String::class.java, path, cb.literal("YYYY")) as Expression<String>
            }
        }
    }
}
