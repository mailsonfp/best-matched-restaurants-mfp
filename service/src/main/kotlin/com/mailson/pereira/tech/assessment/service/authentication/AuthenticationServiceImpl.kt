package com.mailson.pereira.tech.assessment.service.authentication

import com.mailson.pereira.tech.assessment.entities.utils.JwtUtils
import com.mailson.pereira.tech.assessment.entities.utils.RedisUtils
import com.mailson.pereira.tech.assessment.input.authentication.AuthenticationService
import com.mailson.pereira.tech.assessment.input.authentication.dto.AuthenticationParamsRequestDTO
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class AuthenticationServiceImpl(
    private val jwtUtil: JwtUtils,
    private val redisUtils: RedisUtils
): AuthenticationService {
    override fun generateToken(authenticationParams: AuthenticationParamsRequestDTO): Map<String, String> {
        val token = jwtUtil.generateToken(authenticationParams.userName, authenticationParams.authorities)

        redisUtils.storeValue(
            key = "token:${authenticationParams.userName}",
            value = token,
            expirationTime = 30,
            expirationTimeUnit = TimeUnit.MINUTES
        )

        return mapOf("token" to token)
    }

    override fun validateToken(userName: String): String? {
        return validateToken(userName)
    }
}