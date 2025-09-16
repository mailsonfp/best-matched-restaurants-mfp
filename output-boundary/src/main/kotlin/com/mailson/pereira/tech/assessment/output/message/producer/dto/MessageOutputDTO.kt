package com.mailson.pereira.tech.assessment.output.message.producer.dto

import java.math.BigDecimal

data class MessageOutputDTO(
    val restaurantName: String?,
    val distance: Int?,
    val customerRating: Int?,
    val price: BigDecimal?,
    val cuisineName: String?
)
