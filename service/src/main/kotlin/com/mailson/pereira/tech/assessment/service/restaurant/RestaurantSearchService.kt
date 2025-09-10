package com.mailson.pereira.tech.assessment.service.restaurant

import com.mailson.pereira.tech.assessment.input.exceptions.InvalidSearchParamsException
import com.mailson.pereira.tech.assessment.input.restaurant.RestaurantSearchInput
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantMatchedResponseInputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.RestaurantRepository
import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class RestaurantSearchService(
    private val restaurantRepository: RestaurantRepository
): RestaurantSearchInput {

    override fun findBestMatchedRestaurants(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?
    ): List<RestaurantMatchedResponseInputDTO> {
        return if (isValidateRestaurantSearchParams(
                restaurantName,
                distance,
                customerRating,
                price,
                cuisineName)) {
            restaurantRepository.findBestMatchedRestaurants(
                restaurantName,
                distance,
                customerRating,
                price,
                cuisineName
            ).map { toRestaurantMatchedResponseInputDTO(it) }
        } else arrayListOf()
    }

    override fun isValidateRestaurantSearchParams(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?
    ): Boolean {
        val errors = mutableListOf<String>()

        if(restaurantName == null && distance == null && customerRating == null
            && price == null && cuisineName == null)
            errors.add("At least one parameter have to be informed to fulfill the best matched restaurant search")

        customerRating?.let {
            if (it !in 1..5) errors.add("Customer Rating must be between 1 and 5 stars.")
        }

        distance?.let {
            if (it !in 1..10) errors.add("Distance must be between 1 and 10 miles.")
        }

        price?.let {
            if (it < BigDecimal(10) || it > BigDecimal(50)) {
                errors.add("Price must be between $10 and $50.")
            }
        }

        if (errors.isNotEmpty()) {
            throw InvalidSearchParamsException(errors.joinToString(";"))
        }

        return true
    }

    private fun toRestaurantMatchedResponseInputDTO(restaurantOutputDTO: RestaurantOutputDTO) = RestaurantMatchedResponseInputDTO(
        restaurantName = restaurantOutputDTO.name,
        distance = restaurantOutputDTO.distance,
        customerRating = restaurantOutputDTO.customerRating,
        price = restaurantOutputDTO.price,
        cuisineName = restaurantOutputDTO.cuisine.name
    )
}