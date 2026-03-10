package com.mailson.pereira.tech.assessment.web.metric

import com.mailson.pereira.tech.assessment.input.metric.maintenance.MetricMaintenanceInput
import com.mailson.pereira.tech.assessment.input.metric.maintenance.dto.SearchParamsMetricDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus

@Tag(
    name = "Metric Maintenance",
    description = "API for metric maintenance and reporting. " +
            "This controller was specifically designed to generate metrics " +
            "through automated execution using Postman runner. " +
            "It allows posting search parameters and collecting metric data " +
            "for analysis and reporting purposes."
)
@RestController
@RequestMapping(value = ["/v1/metric"])
class MetricController(
    private val searchMetricMaintenance: MetricMaintenanceInput
) {

    @Operation(
        summary = "Generate metrics report",
        description = "Accepts search parameters and generates metric data. " +
                "This endpoint is intended to be executed via Postman runner " +
                "to automate metric collection and reporting."
    )
    @ApiResponse(responseCode = "200", description = "Metrics generated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/maintenance")
    fun generateMetrics(
        @RequestBody searchParams: SearchParamsMetricDTO,
        httpServletRequest: HttpServletRequest
    ) {
        searchMetricMaintenance.generateMetricDataByAPI(searchParams, httpServletRequest)
    }
}