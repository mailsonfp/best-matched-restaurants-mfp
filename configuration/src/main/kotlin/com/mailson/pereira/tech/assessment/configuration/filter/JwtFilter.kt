package com.mailson.pereira.tech.assessment.configuration.filter

import com.mailson.pereira.tech.assessment.entities.utils.JwtUtils
import com.mailson.pereira.tech.assessment.entities.utils.RedisUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtUtils: JwtUtils,
    private val redisUtils: RedisUtils
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer ")) {
            val token = header.substring(7)

            val claims = jwtUtils.getClaims(token)
            val username = claims?.subject

            if (username != null) {
                val storedToken = redisUtils.getValue("token:$username")
                if (storedToken == token) {
                    // extrai authorities do claim "authorities"
                    val authoritiesClaim = claims["authorities"]

                    val authorities = when (authoritiesClaim) {
                        is List<*> -> authoritiesClaim.map { SimpleGrantedAuthority(it.toString()) }
                        is String -> listOf(SimpleGrantedAuthority(authoritiesClaim))
                        else -> emptyList()
                    }

                    val auth = UsernamePasswordAuthenticationToken(username, null, authorities)
                    SecurityContextHolder.getContext().authentication = auth
                }
            }

        }

        filterChain.doFilter(request, response)
    }
}