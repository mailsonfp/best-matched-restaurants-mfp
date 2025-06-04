package com.mailson.pereira.tech.assessment.web.restaurant

import com.mailson.pereira.tech.assessment.input.restaurant.RestaurantSearchInput
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantMatchedResponseInputDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@Tag(name = "Restaurant Search", description = "API for find the best matched restaurants based on search parameters")
@RestController
@RequestMapping(value = ["v1/restaurants/search"], produces = [MediaType.APPLICATION_JSON_VALUE])
class RestaurantSearchController(
    private val searchService: RestaurantSearchInput
) {

    @Operation(summary = "Search for restaurants", description = "Find the best matched restaurants based on search parameters")
    @ApiResponse(responseCode = "200", description = "Restaurants found successfully, or an empty list if no matches are found")
    @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    @GetMapping
    fun searchRestaurants(
        @Parameter(description = "Restaurant name (sent as RequestParam)", required = false)
        @RequestParam(name = "restaurantName", required = false) restaurantName: String?,

        @Parameter(description = "Distance in miles (1-10 miles, sent as RequestParam)", required = false)
        @RequestParam(name = "distance", required = false) distance: Int?,

        @Parameter(description = "Customer rating (1-5 stars, sent as RequestParam)", required = false)
        @RequestParam(name = "customerRating", required = false) customerRating: Int?,

        @Parameter(description = "Average price per person ($10-$50, sent as RequestParam)", required = false)
        @RequestParam(name = "price", required = false) price: BigDecimal?,

        @Parameter(description = "Cuisine name (e.g., Chinese, American, sent as RequestParam)", required = false)
        @RequestParam(name = "cuisineName", required = false) cuisineName: String?
    ): ResponseEntity<List<RestaurantMatchedResponseInputDTO>> {
        return ResponseEntity.ok(
            searchService.findBestMatchedRestaurants(
                restaurantName,
                distance,
                customerRating,
                price,
                cuisineName
            )
        )
    }
}