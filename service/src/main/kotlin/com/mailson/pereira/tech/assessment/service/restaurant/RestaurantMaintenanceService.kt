package com.mailson.pereira.tech.assessment.service.restaurant

import com.mailson.pereira.tech.assessment.input.exceptions.CuisineNotFoundException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantAlreadyExistsException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantIdNotFoundException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantNotFoundException
import com.mailson.pereira.tech.assessment.input.restaurant.RestaurantMaintenanceInput
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantRequestInputDTO
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO
import com.mailson.pereira.tech.assessment.output.cuisine.CuisineRepository
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.RestaurantRepository
import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import com.mailson.pereira.tech.assessment.service.mapper.RestaurantMapper
import org.springframework.stereotype.Service

@Service
class RestaurantMaintenanceService(
    private val restaurantRepository: RestaurantRepository,
    private val cuisineRepository: CuisineRepository,
    private val restaurantMapper: RestaurantMapper
): RestaurantMaintenanceInput {
    override fun save(request: RestaurantRequestInputDTO): com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO {
        val existingCuisine = cuisineRepository.getByName(request.cuisineName) ?: throw CuisineNotFoundException(request.cuisineName)

        val existingRestaurantWithName = restaurantRepository.getByName(request.restaurantName)
        if (existingRestaurantWithName != null) throw RestaurantAlreadyExistsException(request.restaurantName)

        return restaurantMapper.toInputDTO(restaurantRepository.save(restaurantMapper.toOutputDTO(request, existingCuisine)))
    }

    override fun delete(restaurantId: Long) {
        val existingRestaurant = restaurantRepository.getById(restaurantId) ?: throw RestaurantIdNotFoundException(restaurantId.toString())
        restaurantRepository.delete(existingRestaurant)
    }

    override fun getByName(name: String): com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO? {
        val existingRestaurant = restaurantRepository.getByName(name) ?: throw RestaurantNotFoundException(name)
        return restaurantMapper.toInputDTO(existingRestaurant)
    }
}