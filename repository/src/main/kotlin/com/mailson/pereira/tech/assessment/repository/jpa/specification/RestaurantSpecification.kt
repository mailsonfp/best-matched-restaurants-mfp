package com.mailson.pereira.tech.assessment.repository.jpa.specification

import com.mailson.pereira.tech.assessment.repository.domain.Cuisine
import com.mailson.pereira.tech.assessment.repository.domain.Restaurant
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Order
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal

object RestaurantSpecifications {
    fun buildRestaurantsSearchQuery(
        restaurantName: String?,
        distance: Int?,
        customerRating: Int?,
        price: BigDecimal?,
        cuisineName: String?
    ): Specification<Restaurant> {
        return Specification { root, query, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            val orders = mutableListOf<Order>()

            // Join with Cuisine entity
            val cuisineJoin = root.join<Restaurant, Cuisine>("cuisine")

            // conditionalParams
            if (!restaurantName.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%${restaurantName.lowercase()}%"))
            }

            customerRating?.let {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("customerRating"), it))
            }

            distance?.let {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("distance"), it))
            }

            price?.let {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), it))
            }

            if (!cuisineName.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(cuisineJoin.get("name")), "%${cuisineName.lowercase()}%"))
            }

            // Applying sorting rules
            orders.add(criteriaBuilder.asc(root.get<Int>("distance")))
            orders.add(criteriaBuilder.desc(root.get<Int>("customerRating")))
            orders.add(criteriaBuilder.asc(root.get<BigDecimal>("price")))
            orders.add(criteriaBuilder.asc(criteriaBuilder.function("RAND", Double::class.java)))

            query?.orderBy(orders)

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}