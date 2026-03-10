package com.mailson.pereira.tech.assessment.entities.utils

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisUtils(
    private val redisTemplate: StringRedisTemplate
) {
    fun storeValue(
        key: String,
        value: String,
        expirationTime: Long,
        expirationTimeUnit: TimeUnit
    ) {
        val ops = redisTemplate.opsForValue()
        ops.set(key, value, expirationTime, expirationTimeUnit)
    }

    fun getValue(key: String): String? {
        val ops = redisTemplate.opsForValue()
        return ops.get(key)
    }
}