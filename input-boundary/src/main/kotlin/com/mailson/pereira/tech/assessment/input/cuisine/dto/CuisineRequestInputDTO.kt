package com.mailson.pereira.tech.assessment.input.cuisine.dto

import jakarta.validation.constraints.NotBlank

data class CuisineRequestInputDTO(
    @field:NotBlank(message = "name may not be blank")
    val name: String
)
