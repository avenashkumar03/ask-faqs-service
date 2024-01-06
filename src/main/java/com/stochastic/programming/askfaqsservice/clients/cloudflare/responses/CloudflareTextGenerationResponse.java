package com.stochastic.programming.askfaqsservice.clients.cloudflare.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudflareTextGenerationResponse {
    @JsonProperty("result")
    private ModelResponse modelResponse;
    private boolean success;
    private List<String> errors;
    private List<String> messages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ModelResponse {
        private String response;
    }
}
