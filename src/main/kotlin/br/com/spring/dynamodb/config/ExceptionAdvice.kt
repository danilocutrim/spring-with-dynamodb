package br.com.spring.dynamodb.config

import br.com.spring.dynamodb.exception.NotFoundException
import br.com.spring.dynamodb.exception.UnprocessableEntityException
import br.com.spring.dynamodb.model.error.AmazonExceptionBody
import br.com.spring.dynamodb.model.error.AppExceptionBody
import com.amazonaws.AmazonServiceException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [AmazonServiceException::class])
    fun handleAmazonServiceException(
        ex: AmazonServiceException,
        request: WebRequest
    ): ResponseEntity<AmazonExceptionBody> {
        val ourError = AmazonExceptionBody(
            error = ex.errorCode,
            message = ex.errorMessage,
            service = ex.serviceName,
            status = ex.statusCode,
            path = request.contextPath
        )
        return ResponseEntity(ourError, HttpHeaders(), ourError.status)
    }

    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFoundException(
        ex: NotFoundException,
        request: WebRequest
    ): ResponseEntity<AppExceptionBody> {
        val ourError = AppExceptionBody(
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            message = ex.localizedMessage,
            status = HttpStatus.NOT_FOUND.value(),
            path = request.contextPath
        )
        return ResponseEntity(ourError, HttpHeaders(), ourError.status)
    }

    @ExceptionHandler(value = [UnprocessableEntityException::class])
    fun handleUnprocessableEntityException(
        ex: UnprocessableEntityException,
        request: WebRequest
    ): ResponseEntity<AppExceptionBody> {
        val ourError = AppExceptionBody(
            error = HttpStatus.UNPROCESSABLE_ENTITY.reasonPhrase,
            message = ex.localizedMessage,
            status = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            path = request.contextPath
        )
        return ResponseEntity(ourError, HttpHeaders(), ourError.status)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        request: WebRequest,
        ex: Throwable
    ): ResponseEntity<AppExceptionBody> {
        val ourError = AppExceptionBody(
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = ex.localizedMessage,
            status = HttpStatus.BAD_REQUEST.value(),
            path = request.contextPath
        )
        return ResponseEntity(ourError, HttpHeaders(), ourError.status)
    }
}
