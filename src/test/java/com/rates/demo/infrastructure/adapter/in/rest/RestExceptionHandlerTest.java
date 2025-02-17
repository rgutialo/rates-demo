package com.rates.demo.infrastructure.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.rates.demo.infrastructure.adapter.in.rest.dto.error.ErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    @Mock private CommonExceptionHandler commonExceptionHandler;

    @InjectMocks private RestExceptionHandler restExceptionHandler;

    @Test
    void testHandleExceptionWhenHandledByCommonExceptionHandler() throws Exception {
        Exception ex = new Exception("Test exception");
        WebRequest request = mock(WebRequest.class);
        String instance = "testInstance";
        ErrorDetail errorDetail =
                ErrorDetail.builder()
                        .title("Error")
                        .status(HttpStatus.BAD_REQUEST)
                        .detail("Detailed error message")
                        .instance(instance)
                        .build();

        when(commonExceptionHandler.handlerException(any(Exception.class), anyString()))
                .thenReturn(errorDetail);

        ResponseEntity<Object> responseEntity = restExceptionHandler.handlerException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                MediaType.APPLICATION_PROBLEM_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(errorDetail, responseEntity.getBody());
    }

    @Test
    void testHandleExceptionWhenNotHandledByCommonExceptionHandler() throws Exception {
        Exception ex = new Exception("Test exception");
        WebRequest request = mock(WebRequest.class);
        String instance = "testInstance";
        ErrorDetail errorDetail =
                ErrorDetail.builder()
                        .title("Error")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .detail("Internal error")
                        .instance(instance)
                        .build();

        when(commonExceptionHandler.handlerException(any(Exception.class), anyString()))
                .thenReturn(errorDetail);

        ResponseEntity<Object> responseEntity = restExceptionHandler.handlerException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(
                MediaType.APPLICATION_PROBLEM_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void testInstanceFromWebRequest() {
        ServletWebRequest request = mock(ServletWebRequest.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(request.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getRequestURI()).thenReturn("/prices");

        String instance = restExceptionHandler.instanceFrom(request);

        assertNotNull(instance);
        assertEquals("/prices", instance);
    }

    @Test
    void testInstanceFromProblemDetail() {
        ProblemDetail problemDetail = mock(ProblemDetail.class);
        URI uri = URI.create("/prices");
        when(problemDetail.getInstance()).thenReturn(uri);

        String instance = restExceptionHandler.instanceFrom(problemDetail);

        assertEquals("/prices", instance);
    }
}