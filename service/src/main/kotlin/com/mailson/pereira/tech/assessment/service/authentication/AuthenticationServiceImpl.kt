package com.mailson.pereira.tech.assessment.service.authentication

import com.mailson.pereira.tech.assessment.entities.utils.JwtUtils
import com.mailson.pereira.tech.assessment.entities.utils.RedisUtils
import com.mailson.pereira.tech.assessment.input.AuthenticationService
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class AuthenticationServiceImpl(
    private val jwtUtil: JwtUtils,
    private val redisUtils: RedisUtils
): AuthenticationService {
    override fun generateToken(userName: String): Map<String, String> {
        val token = jwtUtil.generateToken(userName)

        redisUtils.storeValue(
            key = "token:$userName",
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