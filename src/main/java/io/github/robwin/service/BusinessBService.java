package io.github.robwin.service;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import io.github.resilience4j.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.robwin.connnector.Connector;
import io.reactivex.Observable;
import javaslang.control.Try;

@Service(value = "businessBService")
public class BusinessBService implements BusinessService  {

    private final Connector backendBConnector;
    private final CircuitBreaker circuitBreaker;

    public BusinessBService(@Qualifier("backendBConnector") Connector backendBConnector,
                            CircuitBreakerRegistry circuitBreakerRegistry, CircuitBreakerProperties circuitBreakerProperties){
        this.backendBConnector = backendBConnector;
        circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendB", () -> circuitBreakerProperties.createCircuitBreakerConfig("backendB"));
    }

    public String failure() {
        return CircuitBreaker.decorateSupplier(circuitBreaker, backendBConnector::failure).get();
    }

    public String success() {
        return CircuitBreaker.decorateSupplier(circuitBreaker, backendBConnector::success).get();
    }

    @Override
    public String ignore() {
        return CircuitBreaker.decorateSupplier(circuitBreaker, backendBConnector::ignoreException).get();
    }

    @Override
    public Try<String> methodWithRecovery() {
        Try.CheckedSupplier<String> backendFunction = CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> backendBConnector.failure());
        return Try.of(backendFunction)
                .recover((throwable) -> recovery(throwable));
    }

    public Observable<String> methodWhichReturnsAStream() {
        return backendBConnector.methodWhichReturnsAStream()
                .timeout(1, TimeUnit.SECONDS)
                .lift(CircuitBreakerOperator.of(circuitBreaker));
    }

    private String recovery(Throwable throwable) {
        // Handle exception and invoke fallback
        return "Hello world from recovery";
    }
}
