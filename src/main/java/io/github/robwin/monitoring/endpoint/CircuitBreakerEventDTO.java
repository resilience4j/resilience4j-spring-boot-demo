package io.github.robwin.monitoring.endpoint;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.robwin.circuitbreaker.CircuitBreaker;
import io.github.robwin.circuitbreaker.event.CircuitBreakerEvent;

@JsonInclude(JsonInclude.Include.NON_NULL)
class CircuitBreakerEventDTO {

    private final String circuitBreakerName;
    private final CircuitBreakerEvent.Type type;
    private final String creationTime;
    private final String errorMessage;
    private final Long durationInMs;
    private final CircuitBreaker.StateTransition stateTransition;

    CircuitBreakerEventDTO(String circuitBreakerName,
                           CircuitBreakerEvent.Type type,
                           String creationTime,
                          String errorMessage,
                           Long durationInMs,
                           CircuitBreaker.StateTransition stateTransition) {
        this.circuitBreakerName = circuitBreakerName;
        this.type = type;
        this.creationTime = creationTime;
        this.errorMessage = errorMessage;
        this.durationInMs = durationInMs;
        this.stateTransition = stateTransition;
    }

    public String getCircuitBreakerName() {
        return circuitBreakerName;
    }

    public CircuitBreakerEvent.Type getType() {
        return type;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Long getDurationInMs() {
        return durationInMs;
    }

    public CircuitBreaker.StateTransition getStateTransition() {
        return stateTransition;
    }
}
