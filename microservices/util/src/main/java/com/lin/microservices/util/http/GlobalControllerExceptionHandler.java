package com.lin.microservices.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

import com.lin.microservices.util.exception.*;

@RestControllerAdvice
class GlobalControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpErrorInfo handleNotFoundExceptions(Exception ex, HttpServletRequest request) {
        return createHttpErrorInfo(HttpStatus.NOT_FOUND, request, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public HttpErrorInfo handleInvalidInputException(Exception ex, HttpServletRequest request) {
        return createHttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, request, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public HttpErrorInfo handleMethodArgumentTypeMismatchException(Exception ex, HttpServletRequest request) {
        return createHttpErrorInfo(HttpStatus.BAD_REQUEST, request, "Bad Arguments");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public HttpErrorInfo handleException(Exception ex, HttpServletRequest request) {
        return createHttpErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, request, "System Error");
    }
    
    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, HttpServletRequest request, String message) {
        final String path = request.getRequestURI();

        LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }
}
