package com.mailson.pereira.tech.assessment.web.auth

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RedisController(private val redisTemplate: StringRedisTemplate) {

    @GetMapping("/data")
    fun getData(): String {
        val ops = redisTemplate.opsForValue()
        ops.set("hello", "world")
        return "Redis says: ${ops.get("hello")}"
    }
}
