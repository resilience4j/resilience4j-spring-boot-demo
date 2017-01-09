package io.github.robwin.config;

import io.github.robwin.circuitbreaker.CircuitBreakerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "circuitbreaker")
public class CircuitBreakerPropertiesRegistry {

    private Map<String, CircuitBreakerProperties> backends = new HashMap<>();

    public CircuitBreakerProperties circuitBreakerProperties(String backend) {
        return backends.get(backend);
    }

    public CircuitBreakerConfig circuitBreakerConfig(String backend) {
        return circuitBreakerConfig(circuitBreakerProperties(backend));
    }

    private CircuitBreakerConfig circuitBreakerConfig(CircuitBreakerProperties circuitBreakerProperties) {
        CircuitBreakerConfig defaultCircuitBreakerConfig = CircuitBreakerConfig.ofDefaults();
        if (circuitBreakerProperties == null) {
            return defaultCircuitBreakerConfig;
        }

        CircuitBreakerConfig.Builder circuitBreakerConfigBuilder = CircuitBreakerConfig.custom();

        if (circuitBreakerProperties.getWaitInterval() != null) {
            circuitBreakerConfigBuilder.waitDurationInOpenState(Duration.ofMillis(circuitBreakerProperties.getWaitInterval()));
        } else {
            circuitBreakerConfigBuilder.waitDurationInOpenState(defaultCircuitBreakerConfig.getWaitDurationInOpenState());
        }

        if (circuitBreakerProperties.getFailureRateThreshold() != null) {
            circuitBreakerConfigBuilder.failureRateThreshold(circuitBreakerProperties.getFailureRateThreshold());
        } else {
            circuitBreakerConfigBuilder.failureRateThreshold((int) defaultCircuitBreakerConfig.getFailureRateThreshold());
        }

        if (circuitBreakerProperties.getRingBufferSizeInClosedState() != null) {
            circuitBreakerConfigBuilder.ringBufferSizeInClosedState(circuitBreakerProperties.getRingBufferSizeInClosedState());
        } else {
            circuitBreakerConfigBuilder.ringBufferSizeInClosedState(defaultCircuitBreakerConfig.getRingBufferSizeInClosedState());
        }

        if (circuitBreakerProperties.getRingBufferSizeInHalfOpenState() != null) {
            circuitBreakerConfigBuilder.ringBufferSizeInHalfOpenState(circuitBreakerProperties.getRingBufferSizeInHalfOpenState());
        } else {
            circuitBreakerConfigBuilder.ringBufferSizeInHalfOpenState(defaultCircuitBreakerConfig.getRingBufferSizeInHalfOpenState());
        }

        circuitBreakerConfigBuilder.recordFailure(e -> (e instanceof IOException));

        return circuitBreakerConfigBuilder.build();
    }

    public Map<String, CircuitBreakerProperties> getBackendMonitors() {
        return backends;
    }
}
