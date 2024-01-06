package com.stochastic.programming.askfaqsservice.clients.cloudflare.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CloudflareCreateEmbeddingsRequest {
    private Set<String> text;
}
