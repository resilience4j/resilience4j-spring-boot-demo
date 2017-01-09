package io.github.robwin.config;

import io.github.robwin.circuitbreaker.CircuitBreakerRegistry;
import io.github.robwin.circuitbreaker.aspect.CircuitBreakerAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class CircuitBreakerConfig {

    @Bean
    public CircuitBreakerPropertiesRegistry backendMonitorPropertiesRegistry() {
        return new CircuitBreakerPropertiesRegistry();
    }

    @Bean
    public io.github.robwin.circuitbreaker.CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }

    @Bean
    public CircuitBreakerAspect backendMonitorAspect(CircuitBreakerRegistry circuitBreakerRegistry) {
        return new CircuitBreakerAspect(backendMonitorPropertiesRegistry(), circuitBreakerRegistry);
    }
}
