package com.lin.microservices.util.http;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "httpStatusCode", "description", "message", "path", "timestamp" })
public class HttpErrorInfo {
    private final ZonedDateTime timestamp;
    private final String path;
    private final HttpStatus httpStatus;
    private final String message;


    public HttpErrorInfo() {
        timestamp = null;
        this.httpStatus = null;
        this.path = null;
        this.message = null;
    }

    public HttpErrorInfo(HttpStatus httpStatus, String path, String message) {
        timestamp = ZonedDateTime.now();
        this.httpStatus = httpStatus;
        this.path = path;
        this.message = message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public int getHttpStatusCode() {
        return httpStatus.value();
    }

    public String getDescription() {
        return httpStatus.getReasonPhrase();
    }

    public String getMessage() {
        return message;
    }
}
