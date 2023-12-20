package com.javatechie.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            try {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("authentication failed");
                }

                authHeader = authHeader.substring(7);

                ServiceInstance serviceInstance = loadBalancerClient.choose("identity-service");
                String authURL = serviceInstance.getUri().toString();
                ResponseEntity<Object> response = restTemplate.postForEntity(authURL + "/auth/validate", new UserDTO(authHeader), Object.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("authentication failed");
                }

                UserDTO userDTO = (UserDTO) response.getBody();
                exchange.getRequest().getHeaders().add("currentUser", objectMapper.writeValueAsString(userDTO));
            } catch (Exception ex) {
                if (validator.isSecured.test(exchange.getRequest())) {
                    throw new RuntimeException(ex.getMessage());
                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
