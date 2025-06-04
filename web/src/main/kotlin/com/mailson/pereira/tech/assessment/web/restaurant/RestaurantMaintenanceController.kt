package com.mailson.pereira.tech.assessment.web.restaurant

import com.mailson.pereira.tech.assessment.input.restaurant.RestaurantMaintenanceInput
import com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantRequestInputDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@Tag(name = "Restaurant Maintenance", description = "API for managing restaurant maintenance operations")
@RestController
@RequestMapping(value = ["v1/restaurant/maintenance"], produces = [MediaType.APPLICATION_JSON_VALUE])
class RestaurantMaintenanceController(
    private val restaurantMaintenanceService: RestaurantMaintenanceInput
) {

    @Operation(summary = "Get restaurant by name", description = "Retrieve restaurant details by its name")
    @ApiResponse(responseCode = "200", description = "Restaurant details retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Restaurant not found")
    @GetMapping("/name")
    fun getRestaurantByName(@RequestParam @Parameter(description = "Restaurant name to search for", required = true)
                            name: String): ResponseEntity<com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO>{
        return ResponseEntity.ok(restaurantMaintenanceService.getByName(name))
    }

    @Operation(summary = "Create a new restaurant", description = "Add a new restaurant to the database")
    @ApiResponse(responseCode = "201", description = "Restaurant created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @PostMapping
    fun createRestaurant(@RequestBody @Validated @Parameter(description = "Restaurant details for creation", required = true)
                         request: RestaurantRequestInputDTO): ResponseEntity<com.mailson.pereira.tech.assessment.input.restaurant.dto.RestaurantResponseInputDTO> {
        return ResponseEntity.created(URI.create("v1/restaurant/maintenance")).body(restaurantMaintenanceService.save(request))
    }

    @Operation(summary = "Delete a restaurant", description = "Remove a restaurant by its ID")
    @ApiResponse(responseCode = "200", description = "Restaurant deleted successfully")
    @ApiResponse(responseCode = "404", description = "Restaurant not found")
    @DeleteMapping("/{restaurantId}")
    fun deleteRestaurant(@PathVariable @Parameter(description = "ID of the restaurant to delete", required = true)
                         restaurantId: Long): ResponseEntity<Any> {
        restaurantMaintenanceService.delete(restaurantId)
        return ResponseEntity.ok().build()
    }
}