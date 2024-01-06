package com.stochastic.programming.askfaqsservice.clients.cloudflare.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CloudflareErrorResponse {
    private List<CloudflareErrorDetails> errors;
    private boolean success;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class CloudflareErrorDetails{
        private String message;
        private String code;
    }
}
