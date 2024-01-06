package com.stochastic.programming.askfaqsservice.exceptions;

import lombok.Getter;

@Getter
public class TextGenerationException extends RuntimeException{
    private String additionalDetails;

    public TextGenerationException(final String message) {
        super(message);
    }

    public TextGenerationException(final String message, final String additionalDetails) {
        super(message);
        this.additionalDetails = additionalDetails;
    }
}
