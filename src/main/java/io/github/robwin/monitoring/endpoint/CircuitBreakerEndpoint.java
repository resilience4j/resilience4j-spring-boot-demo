package io.github.robwin.monitoring.endpoint;

import io.github.robwin.circuitbreaker.CircuitBreaker;
import io.github.robwin.circuitbreaker.CircuitBreakerRegistry;
import javaslang.collection.Seq;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


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
    public ResponseEntity<Seq<String>> invoke() {
        Seq<String> circuitBreakers = circuitBreakerRegistry.getAllCircuitBreakers()
                .map(CircuitBreaker::getName).sorted();
        return ResponseEntity.ok(circuitBreakers);
    }
}
