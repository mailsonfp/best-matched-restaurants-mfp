package com.mailson.pereira.tech.assessment.input.restaurant

import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantMatchedResponseInputDTO
import jakarta.servlet.http.HttpServletRequest
import java.math.BigDecimal

interface RestaurantSearchInput {
    fun findBestMatchedRestaurants(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?,
        httpServletRequest: HttpServletRequest?
    ): List<RestaurantMatchedResponseInputDTO>
    fun validateRestaurantSearchParams(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?
    )
}