package com.mailson.pereira.tech.assessment.service.cuisine

import com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineRequestInputDTO
import com.mailson.pereira.tech.assessment.input.exceptions.CuisineAlreadyExistsException
import com.mailson.pereira.tech.assessment.input.exceptions.CuisineLinkedWithRestaurantException
import com.mailson.pereira.tech.assessment.input.exceptions.CuisineNotFoundException
import com.mailson.pereira.tech.assessment.output.cuisine.CuisineRepository
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import org.springframework.stereotype.Service

@Service
class CuisineService(
    private val cuisineRepository: CuisineRepository
): com.mailson.pereira.tech.assessment.input.cuisine.CuisineInput {
    override fun save(request: CuisineRequestInputDTO): com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineResponseInputDTO {
        val alreadyExistsCuisine = cuisineRepository.getByName(request.name)

        if (alreadyExistsCuisine!=null)
            throw CuisineAlreadyExistsException(request.name)

        return toCuisineResponseInputDTO(cuisineRepository.save(toCuisineOutputDTO(request)))
    }

    override fun getAll(): List<com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineResponseInputDTO> {
        return cuisineRepository.getAll().map {
            toCuisineResponseInputDTO(it)
        }
    }

    override fun delete(cuisineId: Long) {
        val checkCuisineExists = cuisineRepository.getById(cuisineId) ?: throw CuisineNotFoundException(cuisineId.toString())

        val cuisineLinkedWithRestaurant = cuisineRepository.existsRestaurantLinked(cuisineId)

        if (cuisineLinkedWithRestaurant)
            throw CuisineLinkedWithRestaurantException("Forbiden Operation")

        cuisineRepository.delete(checkCuisineExists)
    }

    private fun toCuisineOutputDTO(request: CuisineRequestInputDTO) = CuisineOutputDTO(
        name = request.name
    )

    private fun toCuisineResponseInputDTO(cuisineOutputDTO: CuisineOutputDTO) =
        com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineResponseInputDTO(
            id = cuisineOutputDTO.id!!,
            name = cuisineOutputDTO.name
        )
}