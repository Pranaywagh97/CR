package com.cashrich.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((ClientHttpRequestInterceptor) (httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().add("X-CMC_PRO_API_KEY", "27ab17d1-215f-49e5-9ca4-afd48810c149");
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });
        return restTemplate;
    }
}
