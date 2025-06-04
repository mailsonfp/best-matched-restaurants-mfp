package com.mailson.pereira.tech.assessment.repository.jpa.repositories

import com.mailson.pereira.tech.assessment.repository.domain.Cuisine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface CuisineJPARepository : JpaRepository<Cuisine,Long> {
    fun findByName(name: String): Optional<Cuisine>

    @Query("select count(1) from restaurant where cuisine_id = :cuisineId", nativeQuery = true)
    fun existsRestaurantLinked(cuisineId: Long): Long
}