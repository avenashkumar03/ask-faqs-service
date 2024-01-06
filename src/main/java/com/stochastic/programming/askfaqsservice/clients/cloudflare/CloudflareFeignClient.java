package com.stochastic.programming.askfaqsservice.clients.cloudflare;

import com.stochastic.programming.askfaqsservice.clients.cloudflare.configurations.CloudflareFeignClientConfigurations;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.requests.CloudflareCreateEmbeddingsRequest;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.requests.CloudflareScopedPromptRequest;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.requests.CloudflareUnScopedPromptRequest;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.responses.CloudflareEmbeddingsResponse;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.responses.CloudflareTextGenerationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cloudflareFeignClient",
        configuration = CloudflareFeignClientConfigurations.class,
        url = "${application.configurations.services.cloudflare.host}")
public interface CloudflareFeignClient {

    @PostMapping("/v1/{accountId}/{gatewayName}/workers-ai/@cf/{textEmbeddingModel}")
    CloudflareEmbeddingsResponse createEmbedding(
            @PathVariable final String accountId,
            @PathVariable final String gatewayName,
            @PathVariable final String textEmbeddingModel,
            @RequestBody final CloudflareCreateEmbeddingsRequest cloudflareCreateEmbeddingsRequest);

    @PostMapping("/v1/{accountId}/{gatewayName}/workers-ai/@cf/{textGenerationModel}")
    CloudflareTextGenerationResponse generatePromptResponse(
            @PathVariable final String accountId,
            @PathVariable final String gatewayName,
            @PathVariable final String textGenerationModel,
            @RequestBody final CloudflareScopedPromptRequest cloudflareScopedPromptRequest);

    @PostMapping("/v1/{accountId}/{gatewayName}/workers-ai/@cf/{textGenerationModel}")
    CloudflareTextGenerationResponse generatePromptResponse(
            @PathVariable final String accountId,
            @PathVariable final String gatewayName,
            @PathVariable final String textGenerationModel,
            @RequestBody final CloudflareUnScopedPromptRequest cloudflareUnScopedPromptRequest);
}
