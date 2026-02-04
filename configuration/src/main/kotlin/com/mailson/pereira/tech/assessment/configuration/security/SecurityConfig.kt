package com.mailson.pereira.tech.assessment.configuration.security

import com.mailson.pereira.tech.assessment.configuration.filter.JwtFilter
import com.mailson.pereira.tech.assessment.entities.utils.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtUtil: JwtUtil
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests{ auth ->
                auth.requestMatchers("/v1/authentication/**").permitAll()
                    .requestMatchers("/v1/metric").permitAll()
                    .requestMatchers("/api/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}