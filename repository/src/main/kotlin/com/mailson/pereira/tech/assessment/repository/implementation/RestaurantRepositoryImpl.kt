package com.mailson.pereira.tech.assessment.repository.implementation

import com.mailson.pereira.tech.assessment.output.restaurant.RestaurantRepository
import com.mailson.pereira.tech.assessment.repository.domain.toEntity
import com.mailson.pereira.tech.assessment.repository.domain.toOutputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import com.mailson.pereira.tech.assessment.repository.jpa.repositories.RestaurantJpaRepository
import com.mailson.pereira.tech.assessment.repository.jpa.specification.RestaurantSpecifications
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.math.BigDecimal
import kotlin.jvm.optionals.getOrNull

@Component
class RestaurantRepositoryImpl(
    private val restaurantJpaRepository: RestaurantJpaRepository
): RestaurantRepository{
    override fun save(request: RestaurantOutputDTO): RestaurantOutputDTO {
        return restaurantJpaRepository.save(request.toEntity()).toOutputDTO()
    }

    override fun delete(restaurantOutputDTO: RestaurantOutputDTO) {
        restaurantJpaRepository.delete(restaurantOutputDTO.toEntity())
    }

    override fun getById(restaurantId: Long): RestaurantOutputDTO? {
        return restaurantJpaRepository.findById(restaurantId).getOrNull()?.toOutputDTO()
    }

    override fun getByName(name: String): RestaurantOutputDTO? {
        return restaurantJpaRepository.findByName(name).getOrNull()?.toOutputDTO()
    }

    override fun findBestMatchedRestaurants(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?
    ): List<RestaurantOutputDTO> {
        val specification = RestaurantSpecifications.buildRestaurantsSearchQuery(
            restaurantName,
            distance,
            customerRating,
            price,
            cuisineName
        )

        // using a Page to limit the result up to five records
        val pageable: Pageable = PageRequest.of(0,5)
        return restaurantJpaRepository.findAll(specification, pageable).content.map {
            it.toOutputDTO()
        }
    }
}