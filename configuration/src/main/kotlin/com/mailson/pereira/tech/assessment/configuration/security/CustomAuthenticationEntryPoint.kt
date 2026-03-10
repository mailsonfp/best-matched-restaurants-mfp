package com.mailson.pereira.tech.assessment.configuration.security

import com.google.gson.Gson
import com.mailson.pereira.tech.assessment.entities.entities.GenericException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.status = HttpServletResponse.SC_UNAUTHORIZED
        response?.contentType = "application/json"

        response?.writer?.write(Gson().toJson(buildGenericException())
        )

    }

    private fun buildGenericException() = GenericException(
        timestamp = LocalDateTime.now().toString(),
        statusCode = HttpServletResponse.SC_UNAUTHORIZED,
        messageTitle = "Unauthorized",
        messageDetail = "Authentication is required to access this resource."
    )
}