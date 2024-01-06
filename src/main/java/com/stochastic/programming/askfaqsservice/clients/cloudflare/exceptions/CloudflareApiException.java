package com.stochastic.programming.askfaqsservice.clients.cloudflare.exceptions;

import com.stochastic.programming.askfaqsservice.clients.cloudflare.responses.CloudflareErrorResponse;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public class CloudflareApiException extends RuntimeException {


    private CloudflareErrorResponse deviceIdentifierErrorResponse;
    private final String additionalDetails;
    private final int responseCode;

    public CloudflareApiException(final int responseCode, final String message, final CloudflareErrorResponse deviceIdentifierErrorResponse) {
        super(message);
        this.deviceIdentifierErrorResponse = deviceIdentifierErrorResponse;
        this.additionalDetails = ObjectUtils.isEmpty(deviceIdentifierErrorResponse) ? null : deviceIdentifierErrorResponse.toString();
        this.responseCode = responseCode;
    }

    public CloudflareApiException(final int responseCode, final String message, final String additionalDetails) {
        super(message);
        this.additionalDetails = additionalDetails;
        this.responseCode = responseCode;
    }
}
