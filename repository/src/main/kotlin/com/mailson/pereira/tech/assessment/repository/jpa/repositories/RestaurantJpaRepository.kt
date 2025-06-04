package com.mailson.pereira.tech.assessment.repository.jpa.repositories

import com.mailson.pereira.tech.assessment.repository.domain.Restaurant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional

interface RestaurantJpaRepository  : JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

    fun findByName(name: String): Optional<Restaurant>
}