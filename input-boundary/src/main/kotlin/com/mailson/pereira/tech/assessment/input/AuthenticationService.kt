package com.mailson.pereira.tech.assessment.input

interface AuthenticationService {

    fun generateToken(username: String): Map<String, String>
    fun validateToken(username: String): String?
}