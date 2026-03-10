package com.mailson.pereira.tech.assessment.input.metric.maintenance.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class SearchParamsMetricDTO(

    @Schema(
        description = "Restaurant name used as a filter in the metric search",
        example = "Pizza Place"
    )
    val restaurantName: String?,

    @Schema(
        description = "Distance in miles (valid range: 1–10). Used to filter restaurants by proximity",
        example = "5"
    )
    val distance: Int?,

    @Schema(
        description = "Customer rating (valid range: 1–5 stars). Used to filter restaurants by rating",
        example = "4"
    )
    val customerRating: Int?,

    @Schema(
        description = "Average price per person (valid range: $10–$50). Used to filter restaurants by price",
        example = "25.00"
    )
    val price: BigDecimal?,

    @Schema(
        description = "Cuisine name (e.g., Chinese, American). Used to filter restaurants by cuisine type",
        example = "Chinese"
    )
    val cuisineName: String?,

    @Schema(
        description = "Date of the search (format: yyyy-MM-dd). Used to generate retroactive metrics. " +
                "If omitted, the current date will be considered.",
        example = "2024-03-15"
    )
    val searchDate: String? = null
)
