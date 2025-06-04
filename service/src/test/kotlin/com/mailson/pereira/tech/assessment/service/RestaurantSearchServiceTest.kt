package com.mailson.pereira.tech.assessment.service

import com.mailson.pereira.tech.assessment.input.exceptions.InvalidSearchParamsException
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantSearchParamsDTO
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.RestaurantRepository
import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import com.mailson.pereira.tech.assessment.service.restaurant.RestaurantSearchService
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
class RestaurantSearchServiceTest {

    @Mock
    private lateinit var restaurantRepository: RestaurantRepository

    @InjectMocks
    private lateinit var restaurantSearchService: RestaurantSearchService

    @Test
    fun `should return matched restaurants when search params are valid`() {
        val restaurantName = "Sushi House"
        val distance = 5
        val customerRating = 4
        val price = BigDecimal(30)
        val cuisineName = "Japanese"

        val matchedRestaurants = listOf(
            RestaurantOutputDTO(
                name = "Sushi House",
                distance = 5,
                customerRating = 4,
                price = BigDecimal(30),
                cuisine = CuisineOutputDTO(id = 1L, name = "Japanese")
            )
        )

        whenever(restaurantRepository.findBestMatchedRestaurants(anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull()))
            .thenReturn(matchedRestaurants)

        val result = restaurantSearchService.findBestMatchedRestaurants(
            restaurantName,
            distance,
            customerRating,
            price,
            cuisineName
        )

        assertEquals(1, result.size)
        assertEquals("Sushi House", result[0].restaurantName)
        assertEquals("Japanese", result[0].cuisineName)
    }

    @Test
    fun `should return empty list when search params are invalid`() {
        val restaurantName = "Invalid Restaurant"
        val distance = 20 // Invalid distance
        val customerRating = 6 // Invalid rating
        val price = BigDecimal(100) // Invalid price
        val cuisineName = "Unknown Cuisine"

        assertThrows<InvalidSearchParamsException> {
            restaurantSearchService.findBestMatchedRestaurants(
                restaurantName,
                distance,
                customerRating,
                price,
                cuisineName
            )
        }
    }

    @Test
    fun `should throw InvalidSearchParamsException when distance is out of range`() {
        val searchParams = RestaurantSearchParamsDTO(distance = 20)

        assertThrows<InvalidSearchParamsException> {
            restaurantSearchService.isValidateRestaurantSearchParams(
                restaurantName = null,
                distance = 20,
                customerRating = null,
                price = null,
                cuisineName = null
            )
        }
    }

    @Test
    fun `should throw InvalidSearchParamsException when customer rating is out of range`() {
        val searchParams = RestaurantSearchParamsDTO(customerRating = 6)

        assertThrows<InvalidSearchParamsException> {
            restaurantSearchService.isValidateRestaurantSearchParams(
                restaurantName = null,
                distance = null,
                customerRating = 6,
                price = null,
                cuisineName = null
            )
        }
    }

    @Test
    fun `should throw InvalidSearchParamsException when price is out of range`() {
        assertThrows<InvalidSearchParamsException> {
            restaurantSearchService.isValidateRestaurantSearchParams(
                restaurantName = null,
                distance = null,
                customerRating = null,
                price = BigDecimal(5),
                cuisineName = null
            )
        }
    }

    @Test
    fun `should validate search parameters correctly`() {
        val restaurantName = "Valid Restaurant"
        val distance = 5
        val customerRating = 4
        val price = BigDecimal(30)
        val cuisineName = "Italian"

        val result = restaurantSearchService.isValidateRestaurantSearchParams(
            restaurantName,
            distance,
            customerRating,
            price,
            cuisineName
        )

        assertTrue(result)
    }
}
