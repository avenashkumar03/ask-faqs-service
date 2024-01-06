package com.stochastic.programming.askfaqsservice.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScopedPromptRequest {
    @NotBlank(message = "systemPrompt cannot be empty")
    private String systemPrompt;

    @NotBlank(message = "userPrompt cannot be empty")
    private String userPrompt;

    private String assistantPrompt;
}
