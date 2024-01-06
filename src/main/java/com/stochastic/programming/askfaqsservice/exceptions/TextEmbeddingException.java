package com.stochastic.programming.askfaqsservice.exceptions;

import lombok.Getter;

@Getter
public class TextEmbeddingException extends RuntimeException{
    private String additionalDetails;

    public TextEmbeddingException(final String message) {
        super(message);
    }

    public TextEmbeddingException(final String message, final String additionalDetails) {
        super(message);
        this.additionalDetails = additionalDetails;
    }
}
