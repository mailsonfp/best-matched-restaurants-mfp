package com.mailson.pereira.tech.assessment.repository.domain

import com.mailson.pereira.tech.assessment.output.cuisine.dto.CuisineOutputDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank

@Entity
@Table
data class Cuisine(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null,

    @NotBlank
    @Column
    val name: String
)

fun Cuisine.toOutputDTO() = CuisineOutputDTO (
    id = this.id,
    name = this.name
)

fun CuisineOutputDTO.toEntity() = Cuisine(
    id = this.id,
    name = this.name
)