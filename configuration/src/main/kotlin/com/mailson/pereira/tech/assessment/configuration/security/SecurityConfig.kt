package com.mailson.pereira.tech.assessment.configuration.security

import com.mailson.pereira.tech.assessment.configuration.filter.JwtFilter
import com.mailson.pereira.tech.assessment.entities.utils.JwtUtils
import com.mailson.pereira.tech.assessment.entities.utils.RedisUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val jwtUtils: JwtUtils,
    private val redisUtils: RedisUtils,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests{ auth ->
                auth.requestMatchers("/v*/authentication/**").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/actuator/**").permitAll()
                    .requestMatchers("/v*/metric/**").hasAuthority("METRIC_REPORT")
                    .requestMatchers("/v*/restaurants/search/**").hasAuthority("RESTAURANT_SEARCH")
                    .requestMatchers("/v*/restaurants/maintenance/**").hasAuthority("RESTAURANT_MAINTENANCE")
                    .requestMatchers("/v*/cuisine/**").hasAuthority("CUISINE_MAINTENANCE")
                    .anyRequest().authenticated()
            }
            .exceptionHandling{ exceptions ->
                exceptions
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)

            }
            .addFilterBefore(JwtFilter(jwtUtils, redisUtils), UsernamePasswordAuthenticationFilter::class.java)
            .cors { }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("http://localhost:3000", "http://localhost:3001")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}