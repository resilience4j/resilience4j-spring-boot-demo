package io.github.robwin.circuitbreaker.annotation;

import java.lang.annotation.*;

/**
 * This annotation can be applied to a class or a specific method.
 * Applying it on a class is equivalent to applying it on all its public methods.
 * The annotation enables backend monitoring for all methods where it is applied.
 * Backend monitoring is performed via a circuit breaker.
 * See {@link io.github.robwin.circuitbreaker.CircuitBreaker} for details.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface CircuitBreaker {

    /**
     * Name of the backend monitor.
     */
    String backend();

}
