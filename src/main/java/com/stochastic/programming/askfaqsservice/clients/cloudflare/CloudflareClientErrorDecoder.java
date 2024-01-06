package com.stochastic.programming.askfaqsservice.clients.cloudflare;

import com.stochastic.programming.askfaqsservice.clients.cloudflare.exceptions.CloudflareApiException;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.responses.CloudflareErrorResponse;
import com.stochastic.programming.askfaqsservice.utils.JsonUtils;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class CloudflareClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        final String message = "Please contact customer care team";
        if (ObjectUtils.isEmpty(response)) {
            return new CloudflareApiException(response.status(), message, "When calling cloudflare api, response is received empty for CloudflareClientErrorDecoder");
        }


        if (ObjectUtils.isEmpty(response.request())) {
            return new CloudflareApiException(response.status(), message, "When calling cloudflare api, response.request() is received empty for CloudflareClientErrorDecoder");
        }

        final Optional<String> optResponseBody = getResponseBody(response);

        CloudflareErrorResponse cloudflareErrorResponse = new CloudflareErrorResponse();
        final Optional<CloudflareErrorResponse> optErrorResponse = optResponseBody.isEmpty() ? Optional.empty()
                : JsonUtils.jsonToObject(optResponseBody.get(), CloudflareErrorResponse.class);
        if (optErrorResponse.isPresent()) {
            cloudflareErrorResponse = optErrorResponse.get();
        }

        return new CloudflareApiException(response.status(), message, cloudflareErrorResponse);
    }

    private Optional<String> getResponseBody(Response response) {
        if (ObjectUtils.isEmpty(response.body())) {
            return Optional.empty();
        }

        try {
            return Optional.of(new String(response.body().asInputStream().readAllBytes()));
        } catch (IOException e) {
            log.error("Error reading cloudflare request response: {}", e.getMessage(), e);
        }

        return Optional.empty();
    }
}
