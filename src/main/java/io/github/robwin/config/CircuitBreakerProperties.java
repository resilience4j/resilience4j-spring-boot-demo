package io.github.robwin.config;

import io.github.robwin.circuitbreaker.CircuitBreakerConfig;
import io.github.robwin.exception.BusinessException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "circuitbreaker")
@Component
public class CircuitBreakerProperties {

    private Map<String, BackendProperties> backends = new HashMap<>();

    private BackendProperties getBackendProperties(String backend) {
        return backends.get(backend);
    }

    public CircuitBreakerConfig circuitBreakerConfig(String backend) {
        return circuitBreakerConfig(getBackendProperties(backend));
    }

    private CircuitBreakerConfig circuitBreakerConfig(BackendProperties backendProperties) {
        if (backendProperties == null) {
            return CircuitBreakerConfig.ofDefaults();
        }

        CircuitBreakerConfig.Builder circuitBreakerConfigBuilder = CircuitBreakerConfig.custom();

        if (backendProperties.getWaitInterval() != null) {
            circuitBreakerConfigBuilder.waitDurationInOpenState(Duration.ofMillis(backendProperties.getWaitInterval()));
        }

        if (backendProperties.getFailureRateThreshold() != null) {
            circuitBreakerConfigBuilder.failureRateThreshold(backendProperties.getFailureRateThreshold());
        }

        if (backendProperties.getRingBufferSizeInClosedState() != null) {
            circuitBreakerConfigBuilder.ringBufferSizeInClosedState(backendProperties.getRingBufferSizeInClosedState());
        }

        if (backendProperties.getRingBufferSizeInHalfOpenState() != null) {
            circuitBreakerConfigBuilder.ringBufferSizeInHalfOpenState(backendProperties.getRingBufferSizeInHalfOpenState());
        }

        circuitBreakerConfigBuilder.recordFailure(e -> (!(e instanceof BusinessException)));

        return circuitBreakerConfigBuilder.build();
    }

    public Map<String, BackendProperties> getBackends() {
        return backends;
    }
}
