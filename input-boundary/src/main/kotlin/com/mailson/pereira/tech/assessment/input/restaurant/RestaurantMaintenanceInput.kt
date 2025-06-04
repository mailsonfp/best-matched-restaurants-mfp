package com.mailson.pereira.tech.assessment.input.restaurant

import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantRequestInputDTO

interface RestaurantMaintenanceInput {
    fun save(request: RestaurantRequestInputDTO): com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO
    fun delete(restaurantId: Long)
    fun getByName(name: String): com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO?
}