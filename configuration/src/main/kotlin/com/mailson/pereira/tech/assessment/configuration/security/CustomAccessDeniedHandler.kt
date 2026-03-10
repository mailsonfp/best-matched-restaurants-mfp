package com.mailson.pereira.tech.assessment.configuration.security

import com.google.gson.Gson
import com.mailson.pereira.tech.assessment.entities.entities.GenericException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.status = HttpServletResponse.SC_FORBIDDEN
        response?.contentType = "application/json"

        response?.writer?.write(Gson().toJson(buildGenericException()))

    }

    private fun buildGenericException() = GenericException(
        timestamp = LocalDateTime.now().toString(),
        statusCode = HttpServletResponse.SC_FORBIDDEN,
        messageTitle = "Access Denied",
        messageDetail = "You do not have permission to access this resource."
    )
}