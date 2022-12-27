package dev.rhapsy.d4j.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    @Profile("localhostRouter-noEureka")
    public RouteLocator configLocalNoEureka(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/lol/*").uri("http://localhost:8082")) // redirect to gateway
                .route(r -> r.path("/api/v1/got/*").uri("http://localhost:8083")) // redirect to gateway
                .build();
    }

    @Bean
    @Profile("localhost-eureka")
    public RouteLocator configLocalEureka(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/lol/*").uri("lb://d4j-game-characters"))
                .route(r -> r.path("/api/v1/got/*").uri("lb://d4j-series-characters")).build();
    }

    @Bean
    @Profile("localhost-eureka-cb")
    public RouteLocator configLocalEurekaCircuitBreaker(RouteLocatorBuilder builder) {
        return builder.routes()
                // Microservice 1
                .route(r -> r.path("/api/v1/lol/*")
                        .filters(f -> {
                            f.circuitBreaker(
                                    c -> c.setName("failoverCB")
                                            .setFallbackUri("forward:/api/v1/lol-failover/characters") // redirect on fail to failover MS
                                            .setRouteId("dbFailover"));
                            // Adding JWT Filter for each request
                            f.filter(authFilter);
                            return f;
                        })
                        .uri("lb://d4j-game-characters"))
                .route(r -> r.path("/api/v1/lol-failover/characters").uri("lb://d4j-game-characters-failover")) // register new route as failover
                // Microservice 2
                .route(r -> r.path("/api/v1/got/*")
                        // Adding JWT Filter for each request
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://d4j-series-characters"))
                // Microservice Auth
                .route(r -> r.path("/api/v1/auth/**")
                        .uri("lb://d4j-auth"))
                .build();

    }
}
