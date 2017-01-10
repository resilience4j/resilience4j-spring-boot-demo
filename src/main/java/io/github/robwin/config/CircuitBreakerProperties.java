package io.github.robwin.config;
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

    private int eventBufferSize = 100;

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

    public int getEventBufferSize() {
        return eventBufferSize;
    }

    public void setEventBufferSize(int eventBufferSize) {
        this.eventBufferSize = eventBufferSize;
    }
}
