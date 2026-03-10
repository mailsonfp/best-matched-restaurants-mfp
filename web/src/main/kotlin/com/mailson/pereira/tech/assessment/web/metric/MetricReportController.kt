package com.mailson.pereira.tech.assessment.web.metric

import com.mailson.pereira.tech.assessment.input.metric.report.SearchMetricReportInput
import com.mailson.pereira.tech.assessment.input.metric.report.dto.MetricReportResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Metric Report",
    description = "API for generating metric reports based on average data. " +
            "The periodType can be 'annual', 'monthly' or 'daily'. " +
            "Depending on the chosen periodType, the initialPeriod and finalPeriod " +
            "must be provided in the correct format:\n" +
            "- annual: yyyy (ex: 2024)\n" +
            "- monthly: yyyy-MM (ex: 2024-03)\n" +
            "- daily: yyyy-MM-dd (ex: 2024-03-15)"
)
@RestController
@RequestMapping(value = ["/v1/metric/report"])
class MetricReportController(
    private val searchMetricReportInput: SearchMetricReportInput
) {

    @Operation(
        summary = "Get average metric report",
        description = "Generates a report with average data for the given period type and range"
    )
    @ApiResponse(responseCode = "200", description = "Report generated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid parameters or incorrect period format")
    @GetMapping("/average-data")
    fun getAverageDataReport(
        @Parameter(
            description = "Type of period for the report. Accepted values: 'annual', 'monthly', 'daily'",
            required = true
        )
        @RequestParam(required = true) periodType: String,

        @Parameter(
            description = "Initial period. Format depends on periodType: yyyy (annual), yyyy-MM (monthly), yyyy-MM-dd (daily)",
            required = true
        )
        @RequestParam(required = true) initialPeriod: String,

        @Parameter(
            description = "Final period. Format depends on periodType: yyyy (annual), yyyy-MM (monthly), yyyy-MM-dd (daily)",
            required = true
        )
        @RequestParam(required = true) finalPeriod: String
    ): ResponseEntity<List<MetricReportResponseDTO>> {
        return ResponseEntity.ok(
            searchMetricReportInput.getAverageReportData(
                periodType,
                initialPeriod,
                finalPeriod
            )
        )
    }
}