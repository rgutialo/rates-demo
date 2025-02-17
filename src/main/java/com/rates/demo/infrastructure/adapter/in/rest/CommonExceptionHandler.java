package com.rates.demo.infrastructure.adapter.in.rest;

import com.rates.demo.domain.exception.NotFoundException;
import com.rates.demo.infrastructure.adapter.in.rest.dto.error.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommonExceptionHandler {

    public static final String MESSAGE_UNHANDLED_ERROR = "Unhandled error";

    public ErrorDetail handlerException(Exception ex, String instance) {
        HttpStatus status;
        String detail;

        switch (ex) {
            case NotFoundException nEx -> {
                status = HttpStatus.NOT_FOUND;
                detail = nEx.getMessage();
            }
            default -> {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                detail = MESSAGE_UNHANDLED_ERROR;
            }
        }
        return ErrorDetail.builder().status(status).detail(detail).instance(instance).build();
    }
}
