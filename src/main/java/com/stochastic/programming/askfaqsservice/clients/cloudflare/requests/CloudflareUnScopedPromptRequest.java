package com.stochastic.programming.askfaqsservice.clients.cloudflare.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CloudflareUnScopedPromptRequest {
    @JsonProperty("prompt")
    private String userPrompt;
}
