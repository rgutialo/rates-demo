package com.rates.demo.infrastructure.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rates.demo.domain.exception.NotFoundException;
import com.rates.demo.infrastructure.adapter.in.rest.dto.error.ErrorDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

class CommonExceptionHandlerTest {

    private CommonExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new CommonExceptionHandler();
    }


    @Test
    void testHandlerException_NotFoundException() {
        NotFoundException exception = new NotFoundException("Resource not found");
        String instance = "/resource/456";

        ErrorDetail result = exceptionHandler.handlerException(exception, instance);

        assertNotNull(result, "ErrorDetail should not be null");
        assertEquals(HttpStatus.NOT_FOUND, result.getStatus(), "Status should be BAD_REQUEST");
        assertEquals(
                "Resource not found",
                result.getDetail(),
                "Detail should match the exception message");
        assertEquals(instance, result.getInstance(), "Instance should match the provided value");
    }

    @Test
    void testHandlerException_UnhandledException() {
        Exception exception = new Exception("Unhandled exception");
        String instance = "/unhandled/789";

        ErrorDetail result = exceptionHandler.handlerException(exception, instance);

        assertNotNull(result, "ErrorDetail should not be null");
        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                result.getStatus(),
                "Status should be INTERNAL_SERVER_ERROR");
        assertEquals(
                CommonExceptionHandler.MESSAGE_UNHANDLED_ERROR,
                result.getDetail(),
                "Detail should be the default unhandled error message");
        assertEquals(instance, result.getInstance(), "Instance should match the provided value");
    }
}
