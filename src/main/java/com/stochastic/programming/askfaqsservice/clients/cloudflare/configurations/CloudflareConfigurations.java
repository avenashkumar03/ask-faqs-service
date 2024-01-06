package com.stochastic.programming.askfaqsservice.clients.cloudflare.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.configurations.services.cloudflare")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloudflareConfigurations {
    private String host;
    private String accountId;
    private String accessToken;
    private String textEmbeddingModel;
    private String textGenerationModel;
    private String gatewayName;
}
