package io.github.robwin.monitoring.endpoint;

import io.github.robwin.circuitbreaker.CircuitBreaker;
import io.github.robwin.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Endpoint which lists all CircuitBreaker events.
 *
 **/
@Component
public class CircuitBreakerEndpoint extends AbstractEndpoint {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public CircuitBreakerEndpoint(CircuitBreakerRegistry circuitBreakerRegistry) {
        super("circuitbreaker");
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Override
    public ResponseEntity<List<String>> invoke() {
        List<String> circuitBreakers = circuitBreakerRegistry.getAllCircuitBreakers()
                .map(CircuitBreaker::getName).sorted().toJavaList();
        return ResponseEntity.ok(circuitBreakers);
    }
}
