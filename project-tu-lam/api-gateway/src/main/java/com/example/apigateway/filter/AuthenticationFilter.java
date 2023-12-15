package com.example.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    public static class Config {

    }

    public AuthenticationFilter() {
        super(Config.class);
    }

    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
//            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION) || !exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).equals("Bearer ")) {
//                throw new RuntimeException("missing authorization header");
//            }

//            String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).substring(7);

            return chain.filter(exchange);
        });
    }
}
