package com.stochastic.programming.askfaqsservice.advices;

import com.stochastic.programming.askfaqsservice.clients.cloudflare.exceptions.CloudflareApiException;
import com.stochastic.programming.askfaqsservice.exceptions.TextEmbeddingException;
import com.stochastic.programming.askfaqsservice.exceptions.TextGenerationException;
import com.stochastic.programming.askfaqsservice.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final BindingResult bindingResult = ex.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final Set<String> validationErrors = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet());
        final ErrorResponse error = new ErrorResponse(BAD_REQUEST, "Validation Error", new LinkedHashSet<>(validationErrors));
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(TextEmbeddingException.class)
    public ResponseEntity<ErrorResponse> handleTextEmbeddingException(final TextEmbeddingException exception) {
        log.error("message: {}, additionalDetails: {}",exception.getMessage(), exception.getAdditionalDetails(), exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .message(exception.getMessage())
                .code(INTERNAL_SERVER_ERROR.value())
                .status(INTERNAL_SERVER_ERROR)
                .errorsDetails(Set.of(exception.getMessage()))
                .build());
    }

    @ExceptionHandler(TextGenerationException.class)
    public ResponseEntity<ErrorResponse> handleTextGenerationException(final TextGenerationException exception) {
        log.error("message: {}, additionalDetails: {}",exception.getMessage(), exception.getAdditionalDetails(), exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .message(exception.getMessage())
                .code(INTERNAL_SERVER_ERROR.value())
                .status(INTERNAL_SERVER_ERROR)
                .errorsDetails(Set.of(exception.getMessage()))
                .build());
    }

    @ExceptionHandler(CloudflareApiException.class)
    public ResponseEntity<ErrorResponse> handleCloudflareApiException(final CloudflareApiException exception) {
        log.error("message: {}, additionalDetails: {}",exception.getMessage(), exception.getAdditionalDetails(), exception);
        return ResponseEntity.status(exception.getResponseCode()).body(ErrorResponse.builder()
                .message(exception.getMessage())
                .code(exception.getResponseCode())
                .status(HttpStatus.resolve(exception.getResponseCode()))
                .errorsDetails(Set.of(exception.getMessage()))
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .message(exception.getMessage())
                .code(INTERNAL_SERVER_ERROR.value())
                .status(INTERNAL_SERVER_ERROR)
                .errorsDetails(Set.of(exception.getMessage()))
                .build());
    }
}
