package com.stochastic.programming.askfaqsservice.clients.cloudflare;

import com.stochastic.programming.askfaqsservice.clients.cloudflare.configurations.CloudflareConfigurations;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;


@RequiredArgsConstructor
public class CloudflareClientInterceptor implements RequestInterceptor {
    private final CloudflareConfigurations cloudflareConfigurations;

    @Override
    public void apply(RequestTemplate template) {
        addAuthorizationHeader(template);
    }

    private void addAuthorizationHeader(RequestTemplate template) {
        template.header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", cloudflareConfigurations.getAccessToken()));
    }
}
