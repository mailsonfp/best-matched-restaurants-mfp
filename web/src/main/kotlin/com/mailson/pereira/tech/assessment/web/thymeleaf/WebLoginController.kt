package com.mailson.pereira.tech.assessment.web.thymeleaf

import com.mailson.pereira.tech.assessment.input.authentication.AuthenticationService
import com.mailson.pereira.tech.assessment.input.authentication.dto.AuthenticationParamsRequestDTO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("v1/authentication/web")
class WebLoginController(
    private val authenticationService: AuthenticationService
) {
    @GetMapping("/login")
    fun showLoginPage(model: Model): String {
        return "login" // renderiza login.html
    }

    @PostMapping("/login")
    fun doLogin(
        @RequestParam userName: String,
        @RequestParam authorities: List<String>,
        model: Model
    ): String {
        val tokenMap = authenticationService.generateToken(AuthenticationParamsRequestDTO(userName, ArrayList(authorities)))
        model.addAttribute("token", tokenMap["token"])
        return "token" // renderiza token.html
    }

}