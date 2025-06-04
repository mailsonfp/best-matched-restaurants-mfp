package com.mailson.pereira.tech.assessment.input.restaurant

import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantMatchedResponseInputDTO
import java.math.BigDecimal

interface RestaurantSearchInput {
    fun findBestMatchedRestaurants(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?
    ): List<RestaurantMatchedResponseInputDTO>
    fun isValidateRestaurantSearchParams(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?
    ): Boolean
}