package io.github.robwin;

import io.github.robwin.circuitbreaker.CircuitBreakerRegistry;
import io.github.robwin.circuitbreaker.annotation.EnableCircuitBreaker;
import io.github.robwin.config.CircuitBreakerProperties;
import io.github.robwin.health.CustomHealthIndicator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCircuitBreaker
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public HealthIndicator backendA(CircuitBreakerRegistry circuitBreakerRegistry,
													 CircuitBreakerProperties circuitBreakerProperties){
		return new CustomHealthIndicator(circuitBreakerRegistry, circuitBreakerProperties, "backendA");
	}

	@Bean
	public HealthIndicator backendB(CircuitBreakerRegistry circuitBreakerRegistry,
													 CircuitBreakerProperties circuitBreakerProperties){
		return new CustomHealthIndicator(circuitBreakerRegistry, circuitBreakerProperties, "backendB");
	}
}
