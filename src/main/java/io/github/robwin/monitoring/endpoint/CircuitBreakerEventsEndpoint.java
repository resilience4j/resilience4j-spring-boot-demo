package io.github.robwin.monitoring.endpoint;


import io.github.robwin.circuitbreaker.event.CircuitBreakerEvent;
import io.github.robwin.consumer.CircularEventConsumer;
import javaslang.collection.List;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Component
@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class CircuitBreakerEventsEndpoint extends EndpointMvcAdapter {

    private final CircularEventConsumer<CircuitBreakerEvent> circuitBreakerEventConsumer;

    public CircuitBreakerEventsEndpoint(CircuitBreakerEndpoint circuitBreakerEndpoint,
                                        CircularEventConsumer<CircuitBreakerEvent> circuitBreakerEventConsumer) {
        super(circuitBreakerEndpoint);
        this.circuitBreakerEventConsumer = circuitBreakerEventConsumer;
    }

    @RequestMapping(value = "events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CircuitBreakerEventDTO> getAllCircuitBreakerEvents() {
        return circuitBreakerEventConsumer.getBufferedEvents()
                .map(CircuitBreakerEventDTOFactory::createCircuitBreakerEventDTO);
    }

    @RequestMapping(value = "events/{circuitBreakerName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CircuitBreakerEventDTO> getEventsFilteredByCircuitBreakerName(@PathVariable("circuitBreakerName") String circuitBreakerName) {
        return circuitBreakerEventConsumer.getBufferedEvents()
                .filter(event -> event.getCircuitBreakerName().equals(circuitBreakerName))
                .map(CircuitBreakerEventDTOFactory::createCircuitBreakerEventDTO);
    }

    @RequestMapping(value = "events/{circuitBreakerName}/{eventType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CircuitBreakerEventDTO> getEventsFilteredByCircuitBreakerNameAndEventType(@PathVariable("circuitBreakerName") String circuitBreakerName,
                                                @PathVariable("eventType") String eventType) {
        return circuitBreakerEventConsumer.getBufferedEvents()
                .filter(event -> event.getCircuitBreakerName().equals(circuitBreakerName))
                .filter(event -> event.getEventType() == CircuitBreakerEvent.Type.valueOf(eventType.toUpperCase()))
                .map(CircuitBreakerEventDTOFactory::createCircuitBreakerEventDTO);
    }
}
