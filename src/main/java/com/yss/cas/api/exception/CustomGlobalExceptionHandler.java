package com.yss.cas.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(CustomResponseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse handleCustomException(Exception ex) {
        log.warn("Exception message {}", ex.getMessage());
        log.warn("Exception caused by", ex.getCause());
        return new CustomErrorResponse("Error Custom occured");
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handleAllErrors(Exception ex) {
        log.warn("Exception message {}", ex.getMessage());
        log.warn("Exception caused by", ex.getCause());
        return new CustomErrorResponse("Error occured");
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomErrorResponse {
        private String message;
    }
}
