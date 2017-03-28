package io.github.robwin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.monitoring.health.CircuitBreakerHealthIndicator;
import io.github.resilience4j.consumer.EventConsumerRegistry;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public HealthIndicator backendA(CircuitBreakerRegistry circuitBreakerRegistry,
									EventConsumerRegistry<CircuitBreakerEvent> eventConsumerRegistry,
									CircuitBreakerProperties circuitBreakerProperties){
		return new CircuitBreakerHealthIndicator(circuitBreakerRegistry, eventConsumerRegistry, circuitBreakerProperties, "backendA");
	}

	@Bean
	public HealthIndicator backendB(CircuitBreakerRegistry circuitBreakerRegistry,
									EventConsumerRegistry<CircuitBreakerEvent> eventConsumerRegistry,
									CircuitBreakerProperties circuitBreakerProperties){
		return new CircuitBreakerHealthIndicator(circuitBreakerRegistry, eventConsumerRegistry, circuitBreakerProperties, "backendB");
	}
}
