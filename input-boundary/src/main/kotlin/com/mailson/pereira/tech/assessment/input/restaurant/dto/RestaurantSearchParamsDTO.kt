package com.mailson.pereira.tech.assessment.input.restaurant.dto

import java.math.BigDecimal

data class RestaurantSearchParamsDTO(
    val restaurantName: String? = null,
    val distance: Int? = null,
    val customerRating: Int? = null,
    val price: BigDecimal? = null,
    val cuisineName: String? = null
)