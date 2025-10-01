package com.mailson.pereira.tech.assessment.service.metric.report

import com.mailson.pereira.tech.assessment.entities.enums.SummarizeDataTypeEnum
import com.mailson.pereira.tech.assessment.input.exceptions.InvalidReportParamsException
import com.mailson.pereira.tech.assessment.input.metric.report.SearchMetricReportInput
import com.mailson.pereira.tech.assessment.input.metric.report.dto.MetricReportResponseDTO
import com.mailson.pereira.tech.assessment.input.metric.report.dto.MetricSearchDataResponseDTO
import com.mailson.pereira.tech.assessment.input.metric.report.dto.MetricSearchDetailDataResponseDTO
import com.mailson.pereira.tech.assessment.input.metric.report.dto.MetricSearchDetailItemDataResponseDTO
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
    ): List<MetricReportResponseDTO> {
        validateReportParams(
            periodType,
            initialPeriod,
            finalPeriod
        )
        val metricSearchData = searchMetricReportRepository.summarizeByPeriod(
            SummarizeDataTypeEnum.valueOf(periodType),
            initialPeriod,
            finalPeriod
        )

        val averageNumericParamsByPeriodList = searchMetricReportRepository.averageNumericParamsByPeriod(
            SummarizeDataTypeEnum.valueOf(periodType),
            initialPeriod,
            finalPeriod
        )

        val topParamKeyByPeriodList = searchMetricReportRepository.topParamKeyByPeriod(
            SummarizeDataTypeEnum.valueOf(periodType),
            initialPeriod,
            finalPeriod
        )

        val topParamKeyValueByPeriod = searchMetricReportRepository.topParamKeyValueByPeriod(
            SummarizeDataTypeEnum.valueOf(periodType),
            initialPeriod,
            finalPeriod
        )

        val allPeriods = (metricSearchData.map { it.period } +
                averageNumericParamsByPeriodList.map { it.period } +
                topParamKeyByPeriodList.map { it.period } +
                topParamKeyValueByPeriod.map { it.period })
            .toSet()

        return allPeriods.map { period ->

            val summary = metricSearchData.find { it.period == period }
            val avgItems = averageNumericParamsByPeriodList.filter { it.period == period }.map {
                MetricSearchDetailItemDataResponseDTO(
                    paramKey = it.paramKey,
                    average = it.average
                )
            }

            val topKeyItems = topParamKeyByPeriodList.filter { it.period == period }.map {
                MetricSearchDetailItemDataResponseDTO(
                    paramKey = it.paramKey,
                    total = it.total
                )
            }

            val topKeyValueItems = topParamKeyValueByPeriod.filter { it.period == period }.map {
                MetricSearchDetailItemDataResponseDTO(
                    paramKey = it.paramKey,
                    paramValue = it.paramValue,
                    total = it.total
                )
            }

            val searchMetricSummary = summary?.let {
                MetricSearchDataResponseDTO(
                    totalSearch = it.total,
                    withResultMatched = it.withResult,
                    withoutResultMatched = it.withoutResult
                )
            }

            val searchMetricDetail = if (
                avgItems.isNotEmpty() || topKeyItems.isNotEmpty() || topKeyValueItems.isNotEmpty()
            ) {
                MetricSearchDetailDataResponseDTO(
                    numericDetailParamsAverageInformation = avgItems,
                    topParamKeySearchedInformation = topKeyItems,
                    topParamKeyAndParamValueSearchedInformation = topKeyValueItems
                )
            } else null

            MetricReportResponseDTO(
                period = period,
                searchMetricSummaryInformation = searchMetricSummary,
                searchMetricDetailSummaryInformation = searchMetricDetail
            )
        }.sortedBy { it.period }
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