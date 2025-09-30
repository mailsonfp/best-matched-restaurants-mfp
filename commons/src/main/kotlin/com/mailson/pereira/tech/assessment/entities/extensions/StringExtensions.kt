package com.mailson.pereira.tech.assessment.entities.extensions

import com.mailson.pereira.tech.assessment.entities.enums.SummarizeDataTypeEnum
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

fun String.toLocalDateTimeWithPeriodTypeAndParamType(periodType: SummarizeDataTypeEnum, isInitialParam: Boolean): LocalDateTime {
    return when (periodType) {
        SummarizeDataTypeEnum.DAY -> {
            if(isInitialParam) LocalDate.parse(this).atStartOfDay()
            else LocalDate.parse(this).atTime(23, 59, 59)
        }

        SummarizeDataTypeEnum.MONTH -> {
            if(isInitialParam) YearMonth.parse(this).atDay(1).atStartOfDay()
            else {
                val endMonth = YearMonth.parse(this)
                endMonth.atEndOfMonth().atTime(23, 59, 59)
            }
        }

        SummarizeDataTypeEnum.YEAR -> {
            if(isInitialParam) LocalDate.of(this.toInt(), 1, 1).atStartOfDay()
            else LocalDate.of(this.toInt(), 12, 31).atTime(23, 59, 59)
        }
    }
}