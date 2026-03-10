package com.mailson.pereira.tech.assessment.input.authentication

import com.mailson.pereira.tech.assessment.input.authentication.dto.AuthenticationParamsRequestDTO

interface AuthenticationService {

    fun generateToken(authenticationParams: AuthenticationParamsRequestDTO): Map<String, String>
    fun validateToken(userName: String): String?
}