/*
 * Copyright 2017 Robert Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.robwin.monitoring.consumer;

import io.github.robwin.circuitbreaker.event.CircuitBreakerEvent;
import io.github.robwin.config.CircuitBreakerProperties;
import io.github.robwin.consumer.CircularEventConsumer;
import javaslang.collection.Array;
import javaslang.collection.Seq;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class DefaultEventConsumerRegistry implements EventConsumerRegistry {

    /**
     * The CircularEventConsumers, indexed by name of the backend.
     */
    private final Map<String, CircularEventConsumer<CircuitBreakerEvent>> eventConsumer;

    /**
     * The constructor with default circuitBreaker properties.
     */
    public DefaultEventConsumerRegistry(CircuitBreakerProperties circuitBreakerProperties) {
        this.eventConsumer = new HashMap<>();
        Set<Map.Entry<String, CircuitBreakerProperties.BackendProperties>> entries = circuitBreakerProperties.getBackends().entrySet();
        for(Map.Entry<String, CircuitBreakerProperties.BackendProperties> entry : entries){
            CircuitBreakerProperties.BackendProperties backendProperties = entry.getValue();
            eventConsumer.putIfAbsent(entry.getKey(), new CircularEventConsumer<>(backendProperties.getEventConsumerBufferSize()));
        }
    }

    @Override
    public CircularEventConsumer<CircuitBreakerEvent> getCircularEventConsumer(String circuitBreakerName){
        return eventConsumer.get(circuitBreakerName);
    }

    @Override
    public Seq<CircularEventConsumer<CircuitBreakerEvent>> getAllCircularEventConsumer(){
        return Array.ofAll(eventConsumer.values());
    }
}
