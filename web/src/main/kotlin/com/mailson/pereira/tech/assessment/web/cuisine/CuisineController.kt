package com.mailson.pereira.tech.assessment.web.cuisine

import com.mailson.pereira.tech.assessment.input.cuisine.CuisineInput
import com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineRequestInputDTO
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
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@Tag(name = "Cuisine", description = "API for managing cuisines")
@RestController
@RequestMapping(value = ["/v1/cuisine"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CuisineController(
    private val cuisineService: CuisineInput
) {

    @Operation(summary = "Get all cuisines", description = "Retrieve a list of all available cuisines")
    @ApiResponse(responseCode = "200", description = "List of cuisines retrieved successfully")
    @GetMapping("/all")
    fun getAllCuisines(): ResponseEntity<List<com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineResponseInputDTO>> {
       return ResponseEntity.ok(cuisineService.getAll())
    }

    @Operation(summary = "Create a new cuisine", description = "Add a new cuisine to the database")
    @ApiResponse(responseCode = "201", description = "Cuisine created successfully")
    @PostMapping
    fun createCuisine(@RequestBody @Validated @Parameter(description = "Cuisine data for creation", required = true)
                      request: CuisineRequestInputDTO): ResponseEntity<com.mailson.pereira.tech.assessment.input.cuisine.dto.CuisineResponseInputDTO> {
        return ResponseEntity.created(URI.create("/cuisine")).body(cuisineService.save(request))
    }

    @Operation(summary = "Delete a cuisine", description = "Remove a cuisine by its ID")
    @ApiResponse(responseCode = "200", description = "Cuisine deleted successfully")
    @ApiResponse(responseCode = "404", description = "Cuisine not found")
    @DeleteMapping("/{cuisineId}")
    fun deleteCuisine(@PathVariable @Parameter(description = "ID of the cuisine to delete", required = true)
                      cuisineId: Long): ResponseEntity<Any> {
        cuisineService.delete(cuisineId)
        return ResponseEntity.ok().build()
    }
}