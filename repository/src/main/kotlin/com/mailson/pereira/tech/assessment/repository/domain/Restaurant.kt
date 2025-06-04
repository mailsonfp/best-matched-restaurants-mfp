package com.mailson.pereira.tech.assessment.repository.domain

import com.mailson.pereira.tech.assessment.output.restaurant.dto.RestaurantOutputDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

@Entity
@Table
data class Restaurant(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null,

    @NotBlank
    @Column
    val name: String,

    @Column
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    val customerRating: Int,

    @Column
    @NotNull
    @Min(value = 1)
    val distance: Int,

    @Column
    @NotNull
    @Min(value = 1)
    val price: BigDecimal,

    @OneToOne
    @JoinColumn(name = "cuisine_id", referencedColumnName = "id")
    @NotNull
    val cuisine: Cuisine
)

fun Restaurant.toOutputDTO() = RestaurantOutputDTO(
    id = this.id,
    name = this.name,
    customerRating = this.customerRating,
    distance = this.distance,
    price = this.price,
    cuisine = this.cuisine.toOutputDTO()
)

fun RestaurantOutputDTO.toEntity() = Restaurant(
    id = this.id,
    name = this.name,
    customerRating = this.customerRating,
    distance = this.distance,
    price = this.price,
    cuisine = this.cuisine.toEntity()
)