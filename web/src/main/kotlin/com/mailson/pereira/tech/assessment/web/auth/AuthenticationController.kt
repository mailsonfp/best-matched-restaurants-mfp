package com.mailson.pereira.tech.assessment.web.auth

import com.mailson.pereira.tech.assessment.input.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("login")
    fun doLogin(@RequestHeader userName: String): Map<String, String> {
        return authenticationService.generateToken(userName)
    }
}