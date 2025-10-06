package com.mailson.pereira.tech.assessment.service.mapper

import com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineRequestInputDTO
import com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineResponseInputDTO
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CuisineMapper {
    fun fromName(name: String): CuisineOutputDTO
    fun toName(cuisine: CuisineOutputDTO): String
    fun toOutputDTO(request: CuisineRequestInputDTO): CuisineOutputDTO
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    fun toResponseDTO(output: CuisineOutputDTO): CuisineResponseInputDTO

}
