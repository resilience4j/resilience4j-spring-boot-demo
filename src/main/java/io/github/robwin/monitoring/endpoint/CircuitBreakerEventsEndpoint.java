package io.github.robwin.monitoring.endpoint;


import io.github.robwin.circuitbreaker.event.CircuitBreakerEvent;
import io.github.robwin.consumer.CircularEventConsumer;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
public class CircuitBreakerEventsEndpoint extends EndpointMvcAdapter {

    private final CircularEventConsumer<CircuitBreakerEvent> circuitBreakerEventConsumer;

    public CircuitBreakerEventsEndpoint(CircuitBreakerEndpoint circuitBreakerEndpoint,
                                        CircularEventConsumer<CircuitBreakerEvent> circuitBreakerEventConsumer) {
        super(circuitBreakerEndpoint);
        this.circuitBreakerEventConsumer = circuitBreakerEventConsumer;
    }

    @RequestMapping(value = "{circuitBreakerName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getCircuitBreakerEvents(@PathVariable("circuitBreakerName") String circuitBreakerName) {
        return circuitBreakerEventConsumer.getBufferedEvents()
                .filter(event -> event.getCircuitBreakerName().equals(circuitBreakerName))
                .map(Object::toString)
                .toJavaList();
    }
}
