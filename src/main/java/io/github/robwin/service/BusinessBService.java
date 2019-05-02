package io.github.robwin.service;


import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.robwin.connnector.Connector;
import io.reactivex.Observable;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service(value = "businessBService")
public class BusinessBService implements BusinessService  {

    private final Connector backendBConnector;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public BusinessBService(@Qualifier("backendBConnector") Connector backendBConnector,
                            CircuitBreakerRegistry circuitBreakerRegistry){
        this.backendBConnector = backendBConnector;
        this.circuitBreakerRegistry = circuitBreakerRegistry;

    }

    public String failure() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendB");
        return CircuitBreaker.decorateSupplier(circuitBreaker, backendBConnector::failure).get();
    }

    public String success() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendB");
        return CircuitBreaker.decorateSupplier(circuitBreaker, backendBConnector::success).get();
    }

    @Override
    public String ignore() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendB");
        return CircuitBreaker.decorateSupplier(circuitBreaker, backendBConnector::ignoreException).get();
    }

    @Override
    public Try<String> methodWithRecovery() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendB");
        Supplier<String> backendFunction = CircuitBreaker.decorateSupplier(circuitBreaker, () -> backendBConnector.failure());
        return Try.ofSupplier(backendFunction)
                .recover((throwable) -> recovery(throwable));
    }

    public Observable<String> methodWhichReturnsAStream() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendB");
        return backendBConnector.methodWhichReturnsAStream()
                .timeout(1, TimeUnit.SECONDS)
                .lift(CircuitBreakerOperator.of(circuitBreaker));
    }

    private String recovery(Throwable throwable) {
        // Handle exception and invoke fallback
        return "Hello world from recovery";
    }
}
