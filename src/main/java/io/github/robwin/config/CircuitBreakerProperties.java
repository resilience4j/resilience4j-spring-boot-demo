package io.github.robwin.config;

/**
 * Class storing property values for configuring {@link io.github.robwin.circuitbreaker.CircuitBreaker}s
 * used as backend monitors.
 */
public class CircuitBreakerProperties {

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
