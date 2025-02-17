package com.rates.demo.infrastructure.adapter.in.rest;

import com.rates.demo.infrastructure.adapter.in.rest.dto.error.ErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestExceptionHandler {

    private final CommonExceptionHandler commonExceptionHandler;
    private final ResponseEntityExceptionHandler springHandler =
            new ResponseEntityExceptionHandler() {};

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception ex, WebRequest request) {
        final HttpHeaders headers = new HttpHeaders();
        final String instance = instanceFrom(request);
        final ErrorDetail errorDetail = commonExceptionHandler.handlerException(ex, instance);
        final boolean isNotHandled =
                errorDetail.getStatus().isSameCodeAs(HttpStatus.INTERNAL_SERVER_ERROR);

        if (isNotHandled) {
            try {
                ResponseEntity<Object> springDefaultResponse =
                        springHandler.handleException(ex, request);
                if (springDefaultResponse != null
                        && springDefaultResponse.getBody() instanceof ProblemDetail problemDetail
                        && problemDetail.getStatus() != HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    errorDetail.setTitle(problemDetail.getTitle());
                    errorDetail.setStatus(HttpStatus.resolve(problemDetail.getStatus()));
                    errorDetail.setDetail(problemDetail.getDetail());
                    errorDetail.setInstance(instanceFrom(problemDetail));
                    if (!CollectionUtils.isEmpty(springDefaultResponse.getHeaders())) {
                        headers.addAll(springDefaultResponse.getHeaders());
                    }
                }
            } catch (Exception e) {
                log.warn("Not handled by spring exception handler: ", e);
            }
        }
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        return new ResponseEntity<>(errorDetail, headers, errorDetail.getStatus());
    }

    String instanceFrom(final ProblemDetail problemDetail) {
        return Optional.of(problemDetail)
                .map(ProblemDetail::getInstance)
                .map(URI::toString)
                .orElse("");
    }

    String instanceFrom(WebRequest request) {
        return Optional.of(request)
                .filter(ServletWebRequest.class::isInstance)
                .map(ServletWebRequest.class::cast)
                .map(ServletWebRequest::getRequest)
                .map(HttpServletRequest::getRequestURI)
                .orElse("");
    }
}
