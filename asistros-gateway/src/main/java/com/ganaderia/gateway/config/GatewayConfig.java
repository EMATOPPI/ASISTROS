package com.ganaderia.gateway.config;

import com.ganaderia.gateway.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Auth Service - Rutas públicas (login, register)
                .route("auth-public", r -> r
                        .path("/api/auth/login", "/api/auth/register", "/api/auth/health")
                        .uri("http://localhost:8082"))

                // Auth Service - Rutas protegidas
                .route("auth-protected", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("http://localhost:8082"))

                // Users endpoint - Protegido
//                .route("users-service", r -> r
//                        .path("/api/users/**")
//                        .filters(f -> f.filter(jwtAuthFilter))
//                        .uri("http://localhost:8082"))


                // Animal Service - Todas protegidas
                .route("animal-service", r -> r
                        .path("/api/animals/**")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("http://localhost:8083"))

                .route("breeds-service", r -> r
                        .path("/api/breeds/**")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("http://localhost:8083"))

//                // Health Service - Todas protegidas (futuro)
//                .route("health-service", r -> r
//                        .path("/api/health/**")
//                        .filters(f -> f.filter(jwtAuthFilter))
//                        .uri("http://localhost:8083"))

                .build();
    }
}
