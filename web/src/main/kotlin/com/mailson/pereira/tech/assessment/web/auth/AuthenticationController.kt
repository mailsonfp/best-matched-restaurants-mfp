package com.mailson.pereira.tech.assessment.web.auth

import com.mailson.pereira.tech.assessment.input.authentication.AuthenticationService
import com.mailson.pereira.tech.assessment.input.authentication.dto.AuthenticationParamsRequestDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Authentication",
    description = "API for user authentication and token generation. " +
            "Clients must provide a username and a list of authorities (roles/permissions). " +
            "The service returns a JWT token that can be used to access protected endpoints.\n\n" +
            "Possible authorities:\n" +
            "- RESTAURANT_MAINTENANCE\n" +
            "- CUISINE_MAINTENANCE\n" +
            "- RESTAURANT_SEARCH\n" +
            "- METRIC_REPORT"
)
@RestController
@RequestMapping("v1/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @Operation(
        summary = "Login and generate token",
        description = "Authenticates a user based on username and authorities, and returns a JWT token"
    )
    @ApiResponse(responseCode = "200", description = "Authentication successful, token generated")
    @ApiResponse(responseCode = "400", description = "Invalid authentication parameters")
    @PostMapping("login")
    fun doLogin(
        @Parameter(
            description = "Authentication parameters: username and authorities. " +
                    "Authorities must be one or more of: RESTAURANT_MAINTENANCE, CUISINE_MAINTENANCE, RESTAURANT_SEARCH, METRIC_REPORT",
            required = true
        )
        @RequestBody authenticationParams: AuthenticationParamsRequestDTO
    ): Map<String, String> {
        return authenticationService.generateToken(authenticationParams)
    }
}