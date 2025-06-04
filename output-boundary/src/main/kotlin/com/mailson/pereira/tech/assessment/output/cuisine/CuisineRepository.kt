package com.mailson.pereira.tech.assessment.output.cuisine

import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO

interface CuisineRepository {
    fun save(cuisineOutputDTO: CuisineOutputDTO): CuisineOutputDTO
    fun delete(cuisineOutputDTO: CuisineOutputDTO)
    fun getAll(): List<CuisineOutputDTO>
    fun getByName(name: String): CuisineOutputDTO?
    fun existsRestaurantLinked(cuisineId: Long): Boolean
    fun getById(cuisineId: Long): CuisineOutputDTO?
}