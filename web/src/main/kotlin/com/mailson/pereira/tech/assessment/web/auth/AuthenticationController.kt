package com.mailson.pereira.tech.assessment.web.auth

import com.mailson.pereira.tech.assessment.input.authentication.AuthenticationService
import com.mailson.pereira.tech.assessment.input.authentication.dto.AuthenticationParamsRequestDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("login")
    fun doLogin(@RequestBody authenticationParams: AuthenticationParamsRequestDTO): Map<String, String> {
        return authenticationService.generateToken(authenticationParams)
    }
}