package io.github.robwin.monitoring.endpoint;

import io.github.robwin.circuitbreaker.CircuitBreaker;
import io.github.robwin.circuitbreaker.event.CircuitBreakerEvent;

import java.time.Duration;

class CircuitBreakerEventDTOBuilder {
    private String circuitBreakerName;
    private CircuitBreakerEvent.Type type;
    private String creationTime;
    private String throwable = null;
    private Long duration = null;
    private CircuitBreaker.StateTransition stateTransition = null;

    CircuitBreakerEventDTOBuilder(String circuitBreakerName, CircuitBreakerEvent.Type type, String creationTime){
        this.circuitBreakerName = circuitBreakerName;
        this.type = type;
        this.creationTime = creationTime;
    }

    CircuitBreakerEventDTOBuilder setThrowable(Throwable throwable) {
        this.throwable = throwable.toString();
        return this;
    }

    CircuitBreakerEventDTOBuilder setDuration(Duration duration) {
        this.duration = duration.toMillis();
        return this;
    }

    CircuitBreakerEventDTOBuilder setStateTransition(CircuitBreaker.StateTransition stateTransition) {
        this.stateTransition = stateTransition;
        return this;
    }

    CircuitBreakerEventDTO build() {
        return new CircuitBreakerEventDTO(circuitBreakerName, type, creationTime, throwable, duration, stateTransition);
    }
}