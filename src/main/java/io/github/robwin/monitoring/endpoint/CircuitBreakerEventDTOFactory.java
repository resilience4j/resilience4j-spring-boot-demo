package io.github.robwin.monitoring.endpoint;

import io.github.robwin.circuitbreaker.event.*;

class CircuitBreakerEventDTOFactory {

    static CircuitBreakerEventDTO createCircuitBreakerEventDTO(CircuitBreakerEvent event){
        switch(event.getEventType()) {
            case ERROR:
                CircuitBreakerOnErrorEvent onErrorEvent = (CircuitBreakerOnErrorEvent) event;
                return newCircuitBreakerEventDTOBuilder(onErrorEvent).setThrowable(onErrorEvent.getThrowable()).setDuration(onErrorEvent.getElapsedDuration())
                        .build();
            case SUCCESS:
                CircuitBreakerOnSuccessEvent onSuccessEvent = (CircuitBreakerOnSuccessEvent) event;
                return newCircuitBreakerEventDTOBuilder(onSuccessEvent).setDuration(onSuccessEvent.getElapsedDuration())
                        .build();
            case STATE_TRANSITION:
                CircuitBreakerOnStateTransitionEvent onStateTransitionEvent = (CircuitBreakerOnStateTransitionEvent) event;
                return newCircuitBreakerEventDTOBuilder(onStateTransitionEvent).setStateTransition(onStateTransitionEvent.getStateTransition())
                        .build();
            case IGNORED_ERROR:
                CircuitBreakerOnIgnoredErrorEvent onIgnoredErrorEvent = (CircuitBreakerOnIgnoredErrorEvent) event;
                return newCircuitBreakerEventDTOBuilder(onIgnoredErrorEvent).setThrowable(onIgnoredErrorEvent.getThrowable())
                        .build();
            case NOT_PERMITTED:
                CircuitBreakerOnCallNotPermittedEvent onCallNotPermittedEvent = (CircuitBreakerOnCallNotPermittedEvent) event;
                return newCircuitBreakerEventDTOBuilder(onCallNotPermittedEvent)
                        .build();
            default:
                throw new IllegalArgumentException("Invalid event");
        }
    }

    private static CircuitBreakerEventDTOBuilder newCircuitBreakerEventDTOBuilder(CircuitBreakerEvent event){
        return new CircuitBreakerEventDTOBuilder(event.getCircuitBreakerName(), event.getEventType(), event.getCreationTime().toString());
    }
}
