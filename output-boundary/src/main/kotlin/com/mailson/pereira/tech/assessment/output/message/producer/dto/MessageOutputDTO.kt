package com.mailson.pereira.tech.assessment.output.message.producer.dto

import java.time.LocalDateTime

data class MessageOutputDTO(
    val searchTimestamp: LocalDateTime = LocalDateTime.now(),
    val searchClientIp: String,
    val searchUserAgent: String? = null,
    val searchReferrer: String? = null,
    val searchResultCount: Int = 0,
    val searchOtherMetadata: String? = null,
    val searchParams: List<MessageDetailOutputDTO>
)
