package com.mailson.pereira.tech.assessment.input.message.consumer.metric.dto

import java.time.LocalDateTime

data class MessageInputDTO(
    val searchTimestamp: LocalDateTime,
    val searchClientIp: String,
    val searchUserAgent: String? = null,
    val searchReferrer: String? = null,
    val searchResultCount: Int = 0,
    val searchOtherMetadata: String? = null,
    val searchParams: List<MessageDetailInputDTO>
)
