package com.mailson.pereira.tech.assessment.web.metric

import com.mailson.pereira.tech.assessment.input.metric.report.SearchMetricReportInput
import com.mailson.pereira.tech.assessment.input.metric.report.dto.MetricReportResponseDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/metric/report"])
class MetricReportController(
    private val searchMetricReportInput: SearchMetricReportInput
) {

    @GetMapping("/average-data")
    fun getAverageDataReport(
        @RequestParam(required = true) periodType: String,
        @RequestParam(required = true) initialPeriod: String,
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