package com.mailson.pereira.tech.assessment.repository.implementation

import com.mailson.pereira.tech.assessment.output.cuisine.CuisineRepository
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import com.mailson.pereira.tech.assessment.repository.domain.toEntity
import com.mailson.pereira.tech.assessment.repository.domain.toOutputDTO
import com.mailson.pereira.tech.assessment.repository.jpa.repositories.CuisineJPARepository
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class CuisineRepositoryImpl(
    private val cuisineJPARepository: CuisineJPARepository
): CuisineRepository {
    override fun save(cuisineOutputDTO: CuisineOutputDTO): CuisineOutputDTO {
        return cuisineJPARepository.save(cuisineOutputDTO.toEntity()).toOutputDTO()
    }

    override fun delete(cuisineOutputDTO: CuisineOutputDTO) {
        cuisineJPARepository.delete(cuisineOutputDTO.toEntity())
    }

    override fun getAll(): List<CuisineOutputDTO> {
        return cuisineJPARepository.findAll().map { it.toOutputDTO() }
    }

    override fun getByName(name: String): CuisineOutputDTO? {
        return cuisineJPARepository.findByName(name).getOrNull()?.toOutputDTO()
    }

    override fun existsRestaurantLinked(cuisineId: Long): Boolean {
        val countRestaurantLinked = cuisineJPARepository.existsRestaurantLinked(cuisineId)
        return countRestaurantLinked > 0
    }

    override fun getById(cuisineId: Long): CuisineOutputDTO? {
        return cuisineJPARepository.findById(cuisineId).getOrNull()?.toOutputDTO()
    }
}