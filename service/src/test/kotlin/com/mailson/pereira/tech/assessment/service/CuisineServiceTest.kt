package com.mailson.pereira.tech.assessment.service

import com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineRequestInputDTO
import com.mailson.pereira.tech.assessment.input.exceptions.CuisineAlreadyExistsException
import com.mailson.pereira.tech.assessment.input.exceptions.CuisineLinkedWithRestaurantException
import com.mailson.pereira.tech.assessment.input.exceptions.CuisineNotFoundException
import com.mailson.pereira.tech.assessment.output.cuisine.CuisineRepository
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import com.mailson.pereira.tech.assessment.service.cuisine.CuisineService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.mockito.kotlin.anyOrNull

@ExtendWith(MockitoExtension::class)
class CuisineServiceTest {

    @Mock
    private lateinit var cuisineRepository: CuisineRepository

    @InjectMocks
    private lateinit var cuisineService: CuisineService

    @Test
    fun `should save cuisine successfully with anyOrNull`() {
        val request = CuisineRequestInputDTO(name = "Italian")
        val savedCuisine = CuisineOutputDTO(id = 1L, name = request.name)

        // Using anyOrNull() to allow nullable parameters
        whenever(cuisineRepository.save(anyOrNull())).thenReturn(savedCuisine)

        val result = cuisineService.save(request)

        assertEquals(savedCuisine.id, result.id)
        assertEquals(savedCuisine.name, result.name)
    }

    @Test
    fun `should throw CuisineAlreadyExistsException when cuisine name already exists`() {
        val request = CuisineRequestInputDTO(name = "Mexican")
        val existingCuisine = CuisineOutputDTO(id = 2L, name = request.name)

        Mockito.`when`(cuisineRepository.getByName(request.name)).thenReturn(existingCuisine)

        assertThrows<CuisineAlreadyExistsException> {
            cuisineService.save(request)
        }
    }

    @Test
    fun `should retrieve all cuisines successfully`() {
        val cuisines = listOf(
            CuisineOutputDTO(id = 1L, name = "Italian"),
            CuisineOutputDTO(id = 2L, name = "Japanese")
        )

        Mockito.`when`(cuisineRepository.getAll()).thenReturn(cuisines)

        val result = cuisineService.getAll()

        assertEquals(2, result.size)
        assertEquals("Italian", result[0].name)
        assertEquals("Japanese", result[1].name)
    }

    @Test
    fun `should delete cuisine successfully when it exists and is not linked to a restaurant`() {
        val cuisineId = 1L
        val existingCuisine = CuisineOutputDTO(id = cuisineId, name = "French")

        Mockito.`when`(cuisineRepository.getById(cuisineId)).thenReturn(existingCuisine)
        Mockito.`when`(cuisineRepository.existsRestaurantLinked(cuisineId)).thenReturn(false)

        cuisineService.delete(cuisineId)

        Mockito.verify(cuisineRepository).delete(existingCuisine)
    }

    @Test
    fun `should throw CuisineNotFoundException when trying to delete non-existent cuisine`() {
        val cuisineId = 10L

        Mockito.`when`(cuisineRepository.getById(cuisineId)).thenReturn(null)

        assertThrows<CuisineNotFoundException> {
            cuisineService.delete(cuisineId)
        }
    }

    @Test
    fun `should throw CuisineLinkedWithRestaurantException when trying to delete a linked cuisine`() {
        val cuisineId = 3L
        val existingCuisine = CuisineOutputDTO(id = cuisineId, name = "Chinese")

        Mockito.`when`(cuisineRepository.getById(cuisineId)).thenReturn(existingCuisine)
        Mockito.`when`(cuisineRepository.existsRestaurantLinked(cuisineId)).thenReturn(true)

        assertThrows<CuisineLinkedWithRestaurantException> {
            cuisineService.delete(cuisineId)
        }
    }
}