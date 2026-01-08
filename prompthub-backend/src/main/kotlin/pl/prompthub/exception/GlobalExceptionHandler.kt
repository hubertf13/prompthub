package pl.prompthub.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(
        EmailAlreadyExistsException::class,
        UsernameAlreadyExistsException::class
    )
    fun handleConflictExceptions(
        e: RuntimeException
    ): ResponseEntity<ErrorResponse> =
        buildErrorResponse(e, HttpStatus.CONFLICT)

    @ExceptionHandler(
        UserNotFoundException::class,
        PostNotFoundException::class
    )
    fun handleNotFoundExceptions(
        e: RuntimeException
    ): ResponseEntity<ErrorResponse> =
        buildErrorResponse(e, HttpStatus.NOT_FOUND)

    @ExceptionHandler(
        UnauthorizedOperationException::class,
        TokenIsNotValidException::class,
        BadCredentialsException::class
    )
    fun handleUnauthorizedExceptions(
        e: RuntimeException
    ): ResponseEntity<ErrorResponse> =
        buildErrorResponse(e, HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(
        e: Exception
    ): ResponseEntity<ErrorResponse> {
        log.error("Unexpected exception occurred", e)
        return ResponseEntity(
            ErrorResponse(
                message = "Internal server error",
                status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    private fun buildErrorResponse(
        e: RuntimeException,
        status: HttpStatus
    ): ResponseEntity<ErrorResponse> {
        log.warn("Handled exception: {}", e.message)
        return ResponseEntity(
            ErrorResponse(
                message = e.message ?: "Unexpected error",
                status = status.value()
            ),
            status
        )
    }

    data class ErrorResponse(
        val message: String,
        val status: Int
    )
}
