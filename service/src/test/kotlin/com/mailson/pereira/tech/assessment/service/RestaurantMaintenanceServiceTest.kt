package com.mailson.pereira.tech.assessment.service

import com.mailson.pereira.tech.assessment.input.exceptions.CuisineNotFoundException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantAlreadyExistsException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantIdNotFoundException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantNotFoundException
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantRequestInputDTO
import com.mailson.pereira.tech.assessment.output.cuisine.CuisineRepository
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.RestaurantRepository
import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import com.mailson.pereira.tech.assessment.service.restaurant.RestaurantMaintenanceService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class RestaurantMaintenanceServiceTest {

    @Mock
    private lateinit var restaurantRepository: RestaurantRepository

    @Mock
    private lateinit var cuisineRepository: CuisineRepository

    @InjectMocks
    private lateinit var restaurantMaintenanceService: RestaurantMaintenanceService

    @Test
    fun `should save restaurant successfully when cuisine exists and name is unique`() {
        val request = RestaurantRequestInputDTO(
            restaurantName = "Pasta Heaven",
            distance = 5,
            customerRating = 4,
            price = BigDecimal.valueOf(20),
            cuisineName = "Italian"
        )

        val existingCuisine = CuisineOutputDTO(id = 1L, name = "Italian")
        val savedRestaurant = RestaurantOutputDTO(
            id = 2L,
            name = request.restaurantName,
            distance = request.distance,
            customerRating = request.customerRating,
            price = request.price,
            cuisine = existingCuisine
        )

        whenever(cuisineRepository.getByName(request.cuisineName)).thenReturn(existingCuisine)
        whenever(restaurantRepository.getByName(request.restaurantName)).thenReturn(null)
        whenever(restaurantRepository.save(anyOrNull())).thenReturn(savedRestaurant)

        val result = restaurantMaintenanceService.save(request)

        assertEquals(savedRestaurant.id, result.id)
        assertEquals(savedRestaurant.name, result.name)
        assertEquals(savedRestaurant.cuisine.name, result.cuisineName)
    }

    @Test
    fun `should throw CuisineNotFoundException when saving restaurant with non-existent cuisine`() {
        val request = RestaurantRequestInputDTO(
            restaurantName = "Sushi Spot",
            distance = 3,
            customerRating = 5,
            price = BigDecimal.valueOf(25),
            cuisineName = "Japanese"
        )

        whenever(cuisineRepository.getByName(request.cuisineName)).thenReturn(null)

        assertThrows<CuisineNotFoundException> {
            restaurantMaintenanceService.save(request)
        }
    }

    @Test
    fun `should throw RestaurantAlreadyExistsException when restaurant name already exists`() {
        val request = RestaurantRequestInputDTO(
            restaurantName = "Steak House",
            distance = 7,
            customerRating = 5,
            price = BigDecimal.valueOf(40),
            cuisineName = "American"
        )

        val existingCuisine = CuisineOutputDTO(id = 1L, name = "American")
        val existingRestaurant = RestaurantOutputDTO(
            id = 2L,
            name = request.restaurantName,
            distance = request.distance,
            customerRating = request.customerRating,
            price = request.price,
            cuisine = existingCuisine
        )

        whenever(cuisineRepository.getByName(request.cuisineName)).thenReturn(existingCuisine)
        whenever(restaurantRepository.getByName(request.restaurantName)).thenReturn(existingRestaurant)

        assertThrows<RestaurantAlreadyExistsException> {
            restaurantMaintenanceService.save(request)
        }
    }

    @Test
    fun `should delete restaurant when it exists`() {
        val restaurantId = 1L
        val existingRestaurant = RestaurantOutputDTO(
            id = restaurantId,
            name = "Taco Land",
            distance = 6,
            customerRating = 4,
            price = BigDecimal.valueOf(15),
            cuisine = CuisineOutputDTO(id = 2L, name = "Mexican")
        )

        whenever(restaurantRepository.getById(restaurantId)).thenReturn(existingRestaurant)

        restaurantMaintenanceService.delete(restaurantId)

        org.mockito.kotlin.verify(restaurantRepository).delete(existingRestaurant)
    }

    @Test
    fun `should throw RestaurantIdNotFoundException when deleting non-existent restaurant`() {
        val restaurantId = 99L

        whenever(restaurantRepository.getById(restaurantId)).thenReturn(null)

        assertThrows<RestaurantIdNotFoundException> {
            restaurantMaintenanceService.delete(restaurantId)
        }
    }

    @Test
    fun `should retrieve restaurant by name successfully`() {
        val name = "Pizza Paradise"
        val existingRestaurant = RestaurantOutputDTO(
            id = 5L,
            name = name,
            distance = 4,
            customerRating = 5,
            price = BigDecimal.valueOf(30),
            cuisine = CuisineOutputDTO(id = 3L, name = "Italian")
        )

        whenever(restaurantRepository.getByName(name)).thenReturn(existingRestaurant)

        val result = restaurantMaintenanceService.getByName(name)

        assertEquals(existingRestaurant.id, result?.id)
        assertEquals(existingRestaurant.name, result?.name)
        assertEquals(existingRestaurant.cuisine.name, result?.cuisineName)
    }

    @Test
    fun `should throw RestaurantNotFoundException when retrieving non-existent restaurant`() {
        val name = "Ghost Kitchen"

        whenever(restaurantRepository.getByName(name)).thenReturn(null)

        assertThrows<RestaurantNotFoundException> {
            restaurantMaintenanceService.getByName(name)
        }
    }
}
