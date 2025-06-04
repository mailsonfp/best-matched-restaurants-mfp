package com.mailson.pereira.tech.assessment.input.cuisine

import com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineRequestInputDTO

interface CuisineInput {
    fun save(request: CuisineRequestInputDTO): com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineResponseInputDTO
    fun getAll(): List<com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineResponseInputDTO>
    fun delete(cuisineId: Long)
}