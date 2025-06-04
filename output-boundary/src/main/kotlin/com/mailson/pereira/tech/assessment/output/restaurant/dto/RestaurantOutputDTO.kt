package com.mailson.pereira.tech.assessment.output.restaurant.dto

import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import java.math.BigDecimal

data class RestaurantOutputDTO(
    val id: Long? = null,
    val name: String,
    val customerRating: Int,
    val distance: Int,
    val price: BigDecimal,
    val cuisine: CuisineOutputDTO
)