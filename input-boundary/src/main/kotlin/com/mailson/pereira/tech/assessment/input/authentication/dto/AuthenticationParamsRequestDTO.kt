package com.mailson.pereira.tech.assessment.input.authentication.dto

data class AuthenticationParamsRequestDTO(
    val userName: String,
    val authorities: List<String>
)
