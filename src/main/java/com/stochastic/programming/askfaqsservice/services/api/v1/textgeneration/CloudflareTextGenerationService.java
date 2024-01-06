package com.stochastic.programming.askfaqsservice.services.api.v1.textgeneration;

import com.stochastic.programming.askfaqsservice.clients.cloudflare.CloudflareFeignClient;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.configurations.CloudflareConfigurations;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.requests.CloudflareScopedPromptRequest;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.requests.CloudflareUnScopedPromptRequest;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.responses.CloudflareTextGenerationResponse;
import com.stochastic.programming.askfaqsservice.exceptions.TextGenerationException;
import com.stochastic.programming.askfaqsservice.requests.ScopedPromptRequest;
import com.stochastic.programming.askfaqsservice.requests.UnScopedPromptRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudflareTextGenerationService implements TextGenerationService {
    private final CloudflareFeignClient cloudflareFeignClient;
    private final CloudflareConfigurations cloudflareConfigurations;

    @Override
    public String generatePromptResponse(final ScopedPromptRequest scopedPromptRequest) {
        final CloudflareTextGenerationResponse cloudflareTextGenerationResponse = cloudflareFeignClient.generatePromptResponse(
                cloudflareConfigurations.getAccountId(),
                cloudflareConfigurations.getGatewayName(),
                cloudflareConfigurations.getTextGenerationModel(),
                CloudflareScopedPromptRequest.builder()
                        .scopedPrompts(CloudflareScopedPromptRequest.of(scopedPromptRequest))
                        .build());

        if (ObjectUtils.isEmpty(cloudflareTextGenerationResponse)) {
            throw new TextGenerationException("Failed to generate response.", "Cloudflare text generation response is empty.");
        }

        CloudflareTextGenerationResponse.ModelResponse modelResponse = cloudflareTextGenerationResponse.getModelResponse();
        if (ObjectUtils.isEmpty(modelResponse)) {
            throw new TextGenerationException("Failed to generate response.", "Cloudflare text generation model response is empty.");
        }

        final String promptResponse = modelResponse.getResponse();
        if (ObjectUtils.isEmpty(promptResponse)) {
            throw new TextGenerationException("Failed to generate response.", "Cloudflare text generation model prompt response is empty.");
        }

        return promptResponse;
    }

    @Override
    public String generatePromptResponse(final UnScopedPromptRequest unScopedPromptRequest) {
        final CloudflareTextGenerationResponse cloudflareTextGenerationResponse = cloudflareFeignClient.generatePromptResponse(
                cloudflareConfigurations.getAccountId(),
                cloudflareConfigurations.getGatewayName(),
                cloudflareConfigurations.getTextGenerationModel(),
                CloudflareUnScopedPromptRequest.builder()
                        .userPrompt(unScopedPromptRequest.getUserPrompt())
                        .build());

        if (ObjectUtils.isEmpty(cloudflareTextGenerationResponse)) {
            throw new TextGenerationException("Failed to generate response.", "Cloudflare text generation response is empty.");
        }

        CloudflareTextGenerationResponse.ModelResponse modelResponse = cloudflareTextGenerationResponse.getModelResponse();
        if (ObjectUtils.isEmpty(modelResponse)) {
            throw new TextGenerationException("Failed to generate response.", "Cloudflare text generation model response is empty.");
        }

        final String promptResponse = modelResponse.getResponse();
        if (ObjectUtils.isEmpty(promptResponse)) {
            throw new TextGenerationException("Failed to generate response.", "Cloudflare text generation model prompt response is empty.");
        }

        return promptResponse;
    }
}
