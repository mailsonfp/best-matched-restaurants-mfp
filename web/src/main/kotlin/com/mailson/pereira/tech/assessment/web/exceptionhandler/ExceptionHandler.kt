package com.mailson.pereira.tech.assessment.web.exceptionhandler

import com.fasterxml.jackson.databind.ObjectMapper
import com.mailson.pereira.tech.assessment.input.exceptions.CuisineAlreadyExistsException
import com.mailson.pereira.tech.assessment.input.exceptions.CuisineLinkedWithRestaurantException
import com.mailson.pereira.tech.assessment.input.exceptions.CuisineNotFoundException
import com.mailson.pereira.tech.assessment.input.exceptions.InvalidSearchParamsException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantAlreadyExistsException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantIdNotFoundException
import com.mailson.pereira.tech.assessment.input.exceptions.RestaurantNotFoundException
import com.mailson.pereira.tech.assessment.web.exceptionhandler.dto.GenericException
import com.mailson.pereira.tech.assessment.web.exceptionhandler.dto.GenericExceptionFieldError
import org.slf4j.LoggerFactory
import org.springframework.beans.TypeMismatchException
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.util.*

@ControllerAdvice
open class ExceptionHandler(
    private val messageSource: MessageSource,
    private val objectMapper: ObjectMapper
): ResponseEntityExceptionHandler() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    // handle restaurant already exists exception
    @ExceptionHandler(value = [RestaurantAlreadyExistsException::class])
    protected fun handleRestaurantAlreadyExistsException(
        exception: RestaurantAlreadyExistsException,
        request: WebRequest
    ): ResponseEntity<GenericException> {
        val genericException = getGenericException(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messageCodeTitle = "restaurant.error.already-exists.title",
            messageCode = "restaurant.error.already-exists.message",
            fieldErrors = null,
            messageParam = exception.message!!
        )

        return ResponseEntity(genericException, HttpStatus.BAD_REQUEST)
    }

    // handle restaurant not found exception
    @ExceptionHandler(value = [RestaurantNotFoundException::class])
    protected fun handleRestaurantNotFoundException(
        exception: RestaurantNotFoundException,
        request: WebRequest
    ): ResponseEntity<GenericException> {
        val genericException = getGenericException(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messageCodeTitle = "restaurant.error.not-found.title",
            messageCode = "restaurant.error.not-found.message",
            fieldErrors = null,
            messageParam = exception.message!!
        )

        return ResponseEntity(genericException, HttpStatus.BAD_REQUEST)
    }

    // handle restaurant not found exception
    @ExceptionHandler(value = [RestaurantIdNotFoundException::class])
    protected fun handleRestaurantIdNotFoundException(
        exception: RestaurantIdNotFoundException,
        request: WebRequest
    ): ResponseEntity<GenericException> {
        val genericException = getGenericException(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messageCodeTitle = "restaurant.error.id-not-found.title",
            messageCode = "restaurant.error.id-not-found.message",
            fieldErrors = null,
            messageParam = exception.message!!
        )

        return ResponseEntity(genericException, HttpStatus.BAD_REQUEST)
    }

    // handle cuisine already exists exception
    @ExceptionHandler(value = [CuisineAlreadyExistsException::class])
    protected fun handleCuisineAlreadyExistsException(
        exception: CuisineAlreadyExistsException,
        request: WebRequest
    ): ResponseEntity<GenericException> {
        val genericException = getGenericException(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messageCodeTitle = "cuisine.error.already-exists.title",
            messageCode = "cuisine.error.already-exists.message",
            fieldErrors = null,
            messageParam = exception.message!!
        )

        return ResponseEntity(genericException, HttpStatus.BAD_REQUEST)
    }

    // handle cuisine not found exception
    @ExceptionHandler(value = [CuisineNotFoundException::class])
    protected fun handleCuisineNotFoundException(
        exception: CuisineNotFoundException,
        request: WebRequest
    ): ResponseEntity<GenericException> {
        val genericException = getGenericException(
            statusCode = HttpStatus.NOT_FOUND.value(),
            messageCodeTitle = "cuisine.error.not-found.title",
            messageCode = "cuisine.error.not-found.message",
            fieldErrors = null,
            messageParam = exception.message!!
        )

        return ResponseEntity(genericException, HttpStatus.NOT_FOUND)
    }

    // handle cuisine already exists exception
    @ExceptionHandler(value = [CuisineLinkedWithRestaurantException::class])
    protected fun handleCuisineLinkedWithRestaurantException(
        exception: CuisineLinkedWithRestaurantException,
        request: WebRequest
    ): ResponseEntity<GenericException> {
        val genericException = getGenericException(
            statusCode = HttpStatus.NOT_FOUND.value(),
            messageCodeTitle = "cuisine.error.linked.title",
            messageCode = "cuisine.error.linked.message",
            fieldErrors = null
        )

        return ResponseEntity(genericException, HttpStatus.NOT_FOUND)
    }

    // handle cuisine already exists exception
    @ExceptionHandler(value = [InvalidSearchParamsException::class])
    protected fun handleInvalidSearchParamsException(
        exception: InvalidSearchParamsException,
        request: WebRequest
    ): ResponseEntity<GenericException> {

        val fieldErrorsList = arrayListOf<GenericExceptionFieldError>()
        val errorsMessages = exception.message?.split(";")
        errorsMessages?.forEach {
            fieldErrorsList.add(GenericExceptionFieldError(
                field = "param",
                message = it
            ))
        }

        val genericException = getGenericException(
            statusCode = HttpStatus.NOT_FOUND.value(),
            messageCodeTitle = "restaurant.error.invalid-search-params.title",
            messageCode = "restaurant.error.invalid-search-params.message",
            fieldErrors = fieldErrorsList,
            messageParam = exception.message!!
        )

        return ResponseEntity(genericException, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleUncaught(exception: Exception, request: WebRequest): ResponseEntity<Any>? {

        // in case of an unexpected error, logging the exception for analysis
        logger.error(exception.message,exception)

        val status = HttpStatus.INTERNAL_SERVER_ERROR

        val genericException = getGenericException(
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            messageCodeTitle = "internal.error.title",
            messageCode = "internal.error.message",
            fieldErrors = null
        )

        return handleExceptionInternal(exception, genericException, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        // treatment for message when the body is missing
        val messageParam = if (ex.message!!.contains("Required request body is missing")) "Required request body is missing" else ex.message!!

        val genericException = getGenericException(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messageCodeTitle =  "internal.error.request.title",
            messageCode = "internal.error.request.not-readable.message",
            messageParam = messageParam
        )

        return ResponseEntity(genericException, HttpStatus.BAD_REQUEST)
    }

    // request with no body on request
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest): ResponseEntity<Any>? {

        // treatment for message when the body is missing
        val messageParam = if (ex.message!!.contains("Required request body is missing")) "Required request body is missing" else ex.message!!

        val genericException = getGenericException(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messageCodeTitle =  "internal.error.request.title",
            messageCode = "internal.error.request.not-readable.message",
            messageParam = messageParam
        )

        return ResponseEntity(genericException, HttpStatus.BAD_REQUEST)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = ex.allErrors.map {
            val error = it as FieldError

            GenericExceptionFieldError(
                field = error.field,
                message = error.defaultMessage ?: "error"
            )
        }

        val genericException = getGenericException(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messageCodeTitle = "internal.error.request.title",
            messageCode = "internal.error.request.message",
            fieldErrors = errors
        )

        return handleExceptionInternal(
            ex,
            objectMapper.writeValueAsString(genericException),
            HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request
        )
    }

    override fun handleExceptionInternal(
        ex: java.lang.Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>?  {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request)
    }

    private fun getGenericException(
        statusCode: Int,
        messageCodeTitle: String,
        messageCode: String,
        fieldErrors: List<GenericExceptionFieldError>? = null
    ): GenericException {
        val messageDetail = messageSource.getMessage(messageCode, null, Locale.getDefault())
        val messageTitle = messageSource.getMessage(messageCodeTitle, null, Locale.getDefault())

        return GenericException(
            timestamp = LocalDateTime.now().toString(),
            statusCode = statusCode,
            messageTitle = messageTitle,
            messageDetail = messageDetail,
            fieldErrors = fieldErrors
        )
    }

    private fun getGenericException(
        statusCode: Int,
        messageCodeTitle: String,
        messageCode: String,
        fieldErrors: List<GenericExceptionFieldError>? = null,
        messageParam: String
    ): GenericException {
        val messageDetail = messageSource.getMessage(messageCode, arrayOf(messageParam), Locale.getDefault())
        val messageTitle = messageSource.getMessage(messageCodeTitle, null, Locale.getDefault())

        return GenericException(
            timestamp = LocalDateTime.now().toString(),
            statusCode = statusCode,
            messageTitle = messageTitle,
            messageDetail = messageDetail,
            fieldErrors = fieldErrors
        )
    }
}