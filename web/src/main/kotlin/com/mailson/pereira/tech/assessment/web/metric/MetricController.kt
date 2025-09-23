package com.mailson.pereira.tech.assessment.web.metric

import com.mailson.pereira.tech.assessment.input.metric.maintenance.MetricMaintenanceInput
import com.mailson.pereira.tech.assessment.input.metric.maintenance.dto.SearchParamsMetricDTO
import io.swagger.v3.oas.annotations.headers.Header
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/metric"])
class MetricController(
    private val searchMetricMaintenance: MetricMaintenanceInput
) {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun generateMetricData(
        @RequestBody searchParamsMetricDTO: SearchParamsMetricDTO,
        httpServletRequest: HttpServletRequest
    ) {
        searchMetricMaintenance.generateMetricDataByAPI(
            searchParamsMetricDTO, httpServletRequest
        )

    }
}