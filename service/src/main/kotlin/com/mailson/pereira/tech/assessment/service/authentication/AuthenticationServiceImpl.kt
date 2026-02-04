package com.mailson.pereira.tech.assessment.service.authentication

import com.mailson.pereira.tech.assessment.entities.utils.JwtUtil
import com.mailson.pereira.tech.assessment.input.AuthenticationService
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val jwtUtil: JwtUtil
): AuthenticationService {
    override fun generateToken(username: String): Map<String, String> {
        val token = jwtUtil.generateToken(username)
        return mapOf("token" to token)
    }

    override fun validateToken(username: String): String? {
        return validateToken(username)
    }
}