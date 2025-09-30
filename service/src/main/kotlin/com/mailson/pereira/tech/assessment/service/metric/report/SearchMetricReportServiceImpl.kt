package com.mailson.pereira.tech.assessment.service.metric.report

import com.mailson.pereira.tech.assessment.entities.enums.SummarizeDataTypeEnum
import com.mailson.pereira.tech.assessment.input.exceptions.InvalidReportParamsException
import com.mailson.pereira.tech.assessment.input.exceptions.InvalidSearchParamsException
import com.mailson.pereira.tech.assessment.input.metric.report.SearchMetricReportInput
import com.mailson.pereira.tech.assessment.input.metric.report.dto.AverageDataResponseDTO
import com.mailson.pereira.tech.assessment.input.metric.report.dto.SearchDataResponseDTO
import com.mailson.pereira.tech.assessment.output.metric.SearchMetricReportRepository
import org.springframework.stereotype.Service
import java.time.DateTimeException
import java.time.LocalDate
import java.time.YearMonth

@Service
class SearchMetricReportServiceImpl(
    private val searchMetricReportRepository: SearchMetricReportRepository
): SearchMetricReportInput {

    override fun getAverageReportData(
        periodType: String,
        initialPeriod: String,
        finalPeriod: String
    ): List<AverageDataResponseDTO> {
        validateReportParams(
            periodType,
            initialPeriod,
            finalPeriod
        )
        val metricData = searchMetricReportRepository.summarizeByPeriod(
            SummarizeDataTypeEnum.valueOf(periodType),
            initialPeriod,
            finalPeriod
        )

        return metricData.map {
            AverageDataResponseDTO(
                period = it.period,
                searchSummaryInformation = SearchDataResponseDTO(
                    totalSearch = it.total,
                    withResultMatched = it.withResult,
                    withoutResultMatched = it.withoutResult
                )
            )
        }
    }

    override fun validateReportParams(
        periodType: String,
        initialPeriod: String,
        finalPeriod: String
    ) {
        val errors = mutableListOf<String>()

        val periodTypeEnum = try {
            SummarizeDataTypeEnum.valueOf(periodType)
        } catch (ex: IllegalArgumentException) {
            errors.add("Invalid periodType, possible options:${SummarizeDataTypeEnum.entries.joinToString(",","[", "]")}]")
            throw InvalidReportParamsException(errors.joinToString(";"))
        }

        when (periodTypeEnum) {
            SummarizeDataTypeEnum.DAY -> {
                try {
                    val start = LocalDate.parse(initialPeriod).atStartOfDay()
                    val end = LocalDate.parse(finalPeriod).atTime(23, 59, 59)
                    require(!start.isAfter(end)) { errors.add("Initial Period can not be after Final Period") }
                } catch (ex: DateTimeException){
                    errors.add("Invalid day option, expected format: yyyy-mm-dd")
                }
            }

            SummarizeDataTypeEnum.MONTH -> {
                try {
                    val start = YearMonth.parse(initialPeriod).atDay(1).atStartOfDay()
                    val endMonth = YearMonth.parse(finalPeriod)
                    val end = endMonth.atEndOfMonth().atTime(23, 59, 59)
                    require(!start.isAfter(end)) { errors.add("Initial Month can not be after Final Month") }
                } catch (ex: DateTimeException){
                    errors.add("Invalid month option, expected format: yyyy-mm")
                }
            }

            SummarizeDataTypeEnum.YEAR -> {
                try {
                    val initialYear = initialPeriod.toInt()
                    val finalYear = finalPeriod.toInt()

                    val start = LocalDate.of(initialYear, 1, 1).atStartOfDay()
                    val end = LocalDate.of(finalYear, 12, 31).atTime(23, 59, 59)
                    require(!start.isAfter(end)) { errors.add("Initial Year can not be after Final Year") }
                } catch (ex: IllegalArgumentException) {
                    errors.add("A valid year must be an valid integer value")
                } catch (ex: DateTimeException){
                    errors.add("Invalid year option")
                }
            }
        }

        if (errors.isNotEmpty()) {
            throw InvalidReportParamsException(errors.joinToString(";"))
        }
    }
}