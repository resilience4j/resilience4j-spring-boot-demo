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
