package io.github.robwin.health;


import io.github.robwin.circuitbreaker.CircuitBreaker;
import io.github.robwin.circuitbreaker.CircuitBreakerConfig;
import io.github.robwin.circuitbreaker.CircuitBreakerRegistry;
import io.github.robwin.circuitbreaker.event.CircuitBreakerEvent;
import io.github.robwin.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.robwin.consumer.CircularEventConsumer;
import javaslang.collection.List;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Optional;

public class CustomHealthIndicator implements HealthIndicator {

    private static final String FAILURE_RATE = "failureRate";
    private static final String FAILURE_RATE_THRESHOLD = "failureRateThreshold";
    private static final String BUFFERED_CALLS = "bufferedCalls";
    private static final String FAILED_CALLS = "failedCalls";
    private static final String NOT_PERMITTED = "notPermittedCalls";
    private static final String MAX_BUFFERED_CALLS = "maxBufferedCalls";
    private CircularEventConsumer<CircuitBreakerOnErrorEvent> circularEventConsumer;
    private CircuitBreaker circuitBreaker;

    public CustomHealthIndicator(CircuitBreakerRegistry circuitBreakerRegistry, String backendName) {
        this.circularEventConsumer = new CircularEventConsumer<>(5);
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker(backendName);
        circuitBreaker.getEventStream()
                .filter(event -> event.getEventType() == CircuitBreakerEvent.Type.ERROR)
                .cast(CircuitBreakerOnErrorEvent.class)
                .subscribe(circularEventConsumer);
    }

    @Override
    public Health health() {
        return Optional.of(circuitBreaker)
                .map(this::mapBackendMonitorState)
                .orElse(Health.up().build());
    }

    private Health mapBackendMonitorState(CircuitBreaker circuitBreaker) {
        Health.Builder builder;
        switch (circuitBreaker.getState()) {
            case CLOSED:
                builder = Health.up();
                addDetails(builder, circuitBreaker);
                return builder.build();
            case OPEN:
                builder = Health.down();
                addDetails(builder, circuitBreaker);
                addBufferedExceptions(builder);
                return builder.build();
            case HALF_OPEN:
                builder = Health.unknown();
                addDetails(builder, circuitBreaker);
                addBufferedExceptions(builder);
                return builder.build();
            default:
                builder = Health.unknown();
                addDetails(builder, circuitBreaker);
                addBufferedExceptions(builder);
                return builder.build();
        }
    }

    private void addDetails(Health.Builder builder, CircuitBreaker circuitBreaker) {
        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        CircuitBreakerConfig config = circuitBreaker.getCircuitBreakerConfig();
        builder.withDetail(FAILURE_RATE, metrics.getFailureRate() + "%")
            .withDetail(FAILURE_RATE_THRESHOLD, config.getFailureRateThreshold() + "%")
            .withDetail(MAX_BUFFERED_CALLS, metrics.getMaxNumberOfBufferedCalls())
            .withDetail(BUFFERED_CALLS, metrics.getNumberOfBufferedCalls())
            .withDetail(FAILED_CALLS, metrics.getNumberOfFailedCalls())
            .withDetail(NOT_PERMITTED, metrics.getNumberOfNotPermittedCalls());
    }

    private void addBufferedExceptions(Health.Builder builder) {
        List<String> bufferedExceptions = circularEventConsumer.getBufferedEvents()
                .map(CircuitBreakerOnErrorEvent::toString);
        bufferedExceptions
                .zipWithIndex()
                .forEach(exceptionWithIndex ->
                        builder.withDetail(String.format("Error %d", exceptionWithIndex._2+1), exceptionWithIndex._1));

    }
}
