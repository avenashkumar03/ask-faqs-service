package com.stochastic.programming.askfaqsservice.services.api.v1.embeddings;

import com.stochastic.programming.askfaqsservice.clients.cloudflare.CloudflareFeignClient;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.configurations.CloudflareConfigurations;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.requests.CloudflareCreateEmbeddingsRequest;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.responses.CloudflareEmbeddingsResponse;
import com.stochastic.programming.askfaqsservice.exceptions.TextEmbeddingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudflareEmbeddingsService implements EmbeddingsService {
    private final CloudflareFeignClient cloudflareFeignClient;
    private final CloudflareConfigurations cloudflareConfigurations;

    @Override
    public List<Double> create(final String text) {
        return create(Set.of(text)).get(0);
    }

    @Override
    public List<List<Double>> create(final Set<String> text) {
        final CloudflareEmbeddingsResponse cloudflareEmbeddingsResponse = cloudflareFeignClient.createEmbedding(cloudflareConfigurations.getAccountId(),
                cloudflareConfigurations.getGatewayName(),
                cloudflareConfigurations.getTextEmbeddingModel(),
                CloudflareCreateEmbeddingsRequest.builder()
                        .text(text)
                        .build());

        if (ObjectUtils.isEmpty(cloudflareEmbeddingsResponse)) {
            throw new TextEmbeddingException("Error creating embeddings.", "Cloudflare embeddings response is empty.");
        }


        final CloudflareEmbeddingsResponse.EmbeddingsData embeddingsData = cloudflareEmbeddingsResponse.getEmbeddingsData();
        if (ObjectUtils.isEmpty(embeddingsData)) {
            throw new TextEmbeddingException("Error creating embeddings.", "Cloudflare embeddings response data is empty.");
        }

        final List<List<Double>> embeddings = embeddingsData.getEmbeddings();
        if (ObjectUtils.isEmpty(embeddings)) {
            throw new TextEmbeddingException("Error creating embeddings", "Cloudflare embeddings response data vector is empty.");
        }

        return embeddings;
    }
}
