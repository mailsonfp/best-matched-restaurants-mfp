package com.mailson.pereira.tech.assessment.service.mapper

import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantMatchedResponseInputDTO
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantRequestInputDTO
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [CuisineMapper::class])
interface RestaurantMapper {

    @Mapping(source = "cuisine.name", target = "cuisineName")
    fun toInputDTO(outputDTO: RestaurantOutputDTO): RestaurantResponseInputDTO

    @Mapping(source = "cuisineName", target = "cuisine")
    fun toOutputDTO(inputDTO: RestaurantResponseInputDTO): RestaurantOutputDTO

    @Mapping(source = "request.restauranId", target = "id")
    @Mapping(source = "request.restaurantName", target = "name")
    @Mapping(source = "request.distance", target = "distance")
    @Mapping(source = "request.customerRating", target = "customerRating")
    @Mapping(source = "request.price", target = "price")
    @Mapping(source = "cuisine", target = "cuisine")
    fun toOutputDTO(request: RestaurantRequestInputDTO, cuisine: CuisineOutputDTO): RestaurantOutputDTO

    @Mapping(source = "name", target = "restaurantName")
    @Mapping(source = "cuisine.name", target = "cuisineName")
    fun toMatchedDTO(output: RestaurantOutputDTO): RestaurantMatchedResponseInputDTO

}
