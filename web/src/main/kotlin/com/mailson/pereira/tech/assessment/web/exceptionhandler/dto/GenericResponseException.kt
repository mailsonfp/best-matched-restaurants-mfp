package com.mailson.pereira.tech.assessment.web.exceptionhandler.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GenericException(
    val timestamp: String,
    val statusCode: Int,
    val messageTitle: String,
    val messageDetail: String,
    val fieldErrors: List<GenericExceptionFieldError>? = null
)

data class GenericExceptionFieldError(
    val field: String,
    val message: String
)