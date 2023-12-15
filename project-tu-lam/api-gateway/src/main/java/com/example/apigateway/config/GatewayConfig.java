//package com.example.apigateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("service1_route", r -> r
//                        .path("/api/auth/**") // Điều kiện lọc: URL phù hợp với /service1/**
//                        .uri("http://localhost:8766/api")) // Địa chỉ đích của Service 1
////                .route("service2_route", r -> r
////                        .path("/service2/**") // Điều kiện lọc: URL phù hợp với /service2/**
////                        .uri("http://localhost:8082")) // Địa chỉ đích của Service 2
//                .build();
//    }
//}
