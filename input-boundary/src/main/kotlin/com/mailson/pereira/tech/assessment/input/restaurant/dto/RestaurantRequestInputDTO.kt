package com.mailson.pereira.tech.assessment.input.restaurant.dto

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class RestaurantRequestInputDTO(
    @field:NotBlank(message = "may not be blank")
    val restaurantName:String,

    @field:Min(value = 1, message = "The minimum value permitted is greater than or equal 1 mile")
    @field:Max(value = 10, message = "The maximum value permitted is less than or equal 10 miles")
    val distance: Int,

    @field:Min(value = 1, message = "The minimum value permitted is greater than or equal 1")
    @field:Max(value = 5,message = "The maximum value permitted is less than or equal 5")
    val customerRating: Int,

    @field:DecimalMin(value = "10.00", message = "The minimum value permitted is greater than or equal $10")
    @field:DecimalMax(value = "50.00", message = "The maximum value permitted is less than or equal $50")
    val price: BigDecimal,

    @field:NotBlank(message = "may not be blank")
    val cuisineName: String
)
