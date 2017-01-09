package io.github.robwin.circuitbreaker.annotation;

import io.github.robwin.config.CircuitBreakerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * This annotation should be applied to a SPICA component configuration to enable the SPICA BackendMonitor service.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Documented
@Import(CircuitBreakerConfig.class)
public @interface EnableCircuitBreaker {
}
