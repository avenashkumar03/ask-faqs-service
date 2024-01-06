package com.stochastic.programming.askfaqsservice.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private Integer code;
    private String message;
    private Set<String> errorsDetails;

    public ErrorResponse(final HttpStatus status, final String message, final Set<String> errorDetailsList) {
        super();
        this.status = status;
        this.code = status.value();
        this.message = message;
        this.errorsDetails = errorDetailsList;
    }

    public ErrorResponse(final String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message, message);
    }

    public ErrorResponse(final HttpStatus status, final String message, final String errorDetails) {
        super();
        this.status = status;
        this.code = status.value();
        this.message = message;
        this.errorsDetails = Set.of(errorDetails);
    }
}
