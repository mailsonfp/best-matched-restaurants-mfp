package com.mailson.pereira.tech.assessment.service

import com.google.gson.Gson
import com.mailson.pereira.tech.assessment.input.exceptions.InvalidSearchParamsException
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantMatchedResponseInputDTO
import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import com.mailson.pereira.tech.assessment.output.message.producer.MessageOutputProducer
import com.mailson.pereira.tech.assessment.output.message.producer.dto.MessageOutputDTO
import com.mailson.pereira.tech.assessment.output.restaurant.RestaurantRepository
import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import com.mailson.pereira.tech.assessment.service.mapper.RestaurantMapper
import com.mailson.pereira.tech.assessment.service.restaurant.RestaurantSearchService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class RestaurantSearchServiceTest {

    private class CapturingMessageOutputProducer : MessageOutputProducer {
        val sentMessages = mutableListOf<MessageOutputDTO>()

        override fun sendMessageToSearchQueue(message: MessageOutputDTO) {
            sentMessages.add(message)
        }
    }

    @Mock
    private lateinit var restaurantRepository: RestaurantRepository

    @Mock
    private lateinit var restaurantMapper: RestaurantMapper

    private lateinit var messageOutput: CapturingMessageOutputProducer

    @Mock
    private lateinit var gson: Gson

    private lateinit var restaurantSearchService: RestaurantSearchService

    @BeforeEach
    fun setUp() {
        messageOutput = CapturingMessageOutputProducer()
        restaurantSearchService = RestaurantSearchService(
            restaurantRepository = restaurantRepository,
            restaurantMapper = restaurantMapper,
            messageOutput = messageOutput,
            gson = gson
        )
    }

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
        val mappedRestaurant = RestaurantMatchedResponseInputDTO(
            restaurantName = "Sushi House",
            distance = 5,
            customerRating = 4,
            price = BigDecimal(30),
            cuisineName = "Japanese"
        )

        whenever(restaurantRepository.findBestMatchedRestaurants(anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull()))
            .thenReturn(matchedRestaurants)
        whenever(restaurantMapper.toMatchedDTO(matchedRestaurants[0])).thenReturn(mappedRestaurant)
        whenever(gson.toJson(listOf(mappedRestaurant))).thenReturn("[]")

        val result = restaurantSearchService.findBestMatchedRestaurants(
            restaurantName,
            distance,
            customerRating,
            price,
            cuisineName,
            null
        )

        assertEquals(1, result.size)
        assertEquals("Sushi House", result[0].restaurantName)
        assertEquals("Japanese", result[0].cuisineName)
        assertEquals(1, messageOutput.sentMessages.size)
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
                cuisineName,
                null
            )
        }
    }

    @Test
    fun `should throw InvalidSearchParamsException when distance is out of range`() {
        assertThrows<InvalidSearchParamsException> {
            restaurantSearchService.validateRestaurantSearchParams(
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

        assertThrows<InvalidSearchParamsException> {
            restaurantSearchService.validateRestaurantSearchParams(
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
            restaurantSearchService.validateRestaurantSearchParams(
                restaurantName = null,
                distance = null,
                customerRating = null,
                price = BigDecimal(5),
                cuisineName = null
            )
        }
    }
}
