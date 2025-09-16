package com.mailson.pereira.tech.assessment.service.mapper

import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CuisineMapper {
    fun fromName(name: String): CuisineOutputDTO
    fun toName(cuisine: CuisineOutputDTO): String
}
