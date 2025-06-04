package com.mailson.pereira.tech.assessment.input.restaurant.dto

import java.math.BigDecimal

data class RestaurantResponseInputDTO (
    val id: Long,
    val name:String,
    val distance: Int,
    val customerRating: Int,
    val price: BigDecimal,
    val cuisineName: String
)