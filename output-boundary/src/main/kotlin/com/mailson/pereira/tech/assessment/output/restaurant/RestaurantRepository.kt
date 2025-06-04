package com.mailson.pereira.tech.assessment.output.restaurant

import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import java.math.BigDecimal

interface RestaurantRepository {
    fun save(request: RestaurantOutputDTO): RestaurantOutputDTO
    fun delete(restaurantOutputDTO: RestaurantOutputDTO)
    fun getById(restaurantId: Long): RestaurantOutputDTO?
    fun getByName(name: String): RestaurantOutputDTO?
    fun findBestMatchedRestaurants(
        restaurantName: String? = null,
        customerRating: Int? = null,
        distance: Int? = null,
        price: BigDecimal? = null,
        cuisineName: String? = null
    ): List<RestaurantOutputDTO>
}