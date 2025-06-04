package com.mailson.pereira.tech.assessment.service.restaurant

import com.mailson.pereira.tech.assessment.input.exceptions.CuisineNotFoundException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantAlreadyExistsException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantIdNotFoundException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantNotFoundException
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantRequestInputDTO
import com.mailson.pereira.tech.assessment.output.cuisine.CuisineRepository
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.RestaurantRepository
import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import org.springframework.stereotype.Service

@Service
class RestaurantMaintenanceService(
    private val restaurantRepository: RestaurantRepository,
    private val cuisineRepository: CuisineRepository
): com.mailson.pereira.tech.assessment.input.restaurant.RestaurantMaintenanceInput {
    override fun save(request: RestaurantRequestInputDTO): com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO {
        val existingCuisine = cuisineRepository.getByName(request.cuisineName) ?: throw CuisineNotFoundException(request.cuisineName)

        val existingRestaurantWithName = restaurantRepository.getByName(request.restaurantName)
        if (existingRestaurantWithName != null) throw RestaurantAlreadyExistsException(request.restaurantName)

        return toRestaurantResponseInputDTO(restaurantRepository.save(toRestaurantOutputDTO(request, existingCuisine)))
    }

    override fun delete(restaurantId: Long) {
        val existingRestaurant = restaurantRepository.getById(restaurantId) ?: throw RestaurantIdNotFoundException(restaurantId.toString())
        restaurantRepository.delete(existingRestaurant)
    }

    override fun getByName(name: String): com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO? {
        val existingRestaurant = restaurantRepository.getByName(name) ?: throw RestaurantNotFoundException(name)
        return toRestaurantResponseInputDTO(existingRestaurant)
    }

    private fun toRestaurantResponseInputDTO(restaurantOutputDTO: RestaurantOutputDTO) =
        com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO(
            id = restaurantOutputDTO.id!!,
            name = restaurantOutputDTO.name,
            distance = restaurantOutputDTO.distance,
            customerRating = restaurantOutputDTO.customerRating,
            price = restaurantOutputDTO.price,
            cuisineName = restaurantOutputDTO.cuisine.name
        )

    private fun toRestaurantOutputDTO(request: RestaurantRequestInputDTO, cuisineOutputDTO: CuisineOutputDTO) =
        RestaurantOutputDTO(
            name = request.restaurantName,
            distance = request.distance,
            customerRating = request.customerRating,
            price = request.price,
            cuisine = cuisineOutputDTO
        )

}