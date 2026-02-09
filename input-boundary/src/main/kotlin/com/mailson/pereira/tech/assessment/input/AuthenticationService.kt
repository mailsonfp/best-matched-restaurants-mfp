package com.mailson.pereira.tech.assessment.input

interface AuthenticationService {

    fun generateToken(userName: String): Map<String, String>
    fun validateToken(userName: String): String?
}