package com.stochastic.programming.askfaqsservice.clients.cloudflare.configurations;

import com.stochastic.programming.askfaqsservice.clients.cloudflare.CloudflareClientErrorDecoder;
import com.stochastic.programming.askfaqsservice.clients.cloudflare.CloudflareClientInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * This class is used to configure feign client for device-identifier service
 * Note: Do not put @Configuration annotation in this class, otherwise SpringBoot will
 * use this class as configuration class for all the Feign clients
 */
@RequiredArgsConstructor
public class CloudflareFeignClientConfigurations {

    private final CloudflareConfigurations cloudflareConfigurations;

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CloudflareClientErrorDecoder();
    }

    @Bean
    public RequestInterceptor cloudflareRequestInterceptor() {
        return new CloudflareClientInterceptor(cloudflareConfigurations);
    }
}
