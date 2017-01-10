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
 */package io.github.robwin.config;

/**
 * Class storing property values for configuring {@link io.github.robwin.circuitbreaker.CircuitBreaker}s
 * used as backend monitors.
 */
public class BackendProperties {

    private Integer waitInterval;

    private Integer failureRateThreshold;

    private Integer ringBufferSizeInClosedState;

    private Integer ringBufferSizeInHalfOpenState;


    /**
     * Returns the wait duration in seconds the CircuitBreaker will stay open, before it switches to half closed.
     *
     * @return the wait duration
     */
    public Integer getWaitInterval() {
        return waitInterval;
    }

    /**
     * Sets the wait duration in seconds the CircuitBreaker should stay open, before it switches to half closed.
     *
     * @param waitInterval the wait duration
     */
    public void setWaitInterval(Integer waitInterval) {
        this.waitInterval = waitInterval;
    }

    /**
     * Returns the failure rate threshold for the circuit breaker as percentage.
     *
     * @return the failure rate threshold
     */
    public Integer getFailureRateThreshold() {
        return failureRateThreshold;
    }

    /**
     * Sets the failure rate threshold for the circuit breaker as percentage.
     *
     * @param failureRateThreshold the failure rate threshold
     */
    public void setFailureRateThreshold(Integer failureRateThreshold) {
        this.failureRateThreshold = failureRateThreshold;
    }

    /**
     * Returns the ring buffer size for the circuit breaker while in closed state.
     *
     * @return the ring buffer size
     */
    public Integer getRingBufferSizeInClosedState() {
        return ringBufferSizeInClosedState;
    }

    /**
     * Sets the ring buffer size for the circuit breaker while in closed state.
     *
     * @param ringBufferSizeInClosedState the ring buffer size
     */
    public void setRingBufferSizeInClosedState(Integer ringBufferSizeInClosedState) {
        this.ringBufferSizeInClosedState = ringBufferSizeInClosedState;
    }

    /**
     * Returns the ring buffer size for the circuit breaker while in half open state.
     *
     * @return the ring buffer size
     */
    public Integer getRingBufferSizeInHalfOpenState() {
        return ringBufferSizeInHalfOpenState;
    }

    /**
     * Sets the ring buffer size for the circuit breaker while in half open state.
     *
     * @param ringBufferSizeInHalfOpenState the ring buffer size
     */
    public void setRingBufferSizeInHalfOpenState(Integer ringBufferSizeInHalfOpenState) {
        this.ringBufferSizeInHalfOpenState = ringBufferSizeInHalfOpenState;
    }
}
