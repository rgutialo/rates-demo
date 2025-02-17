package com.rates.demo.infrastructure.adapter.in.rest.dto.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.net.URI;
import java.util.Optional;

@Data
@Builder
public class ErrorDetail {

    private static final URI BLANK_TYPE = URI.create("about:blank");

    private String title;
    private HttpStatusCode status;
    private String detail;
    private String instance;

    public String getTitle() {
        return this.title == null
                ? Optional.ofNullable(status)
                        .filter(HttpStatus.class::isInstance)
                        .map(HttpStatus.class::cast)
                        .map(HttpStatus::getReasonPhrase)
                        .orElse(null)
                : this.title;
    }
}
