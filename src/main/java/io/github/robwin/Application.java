package io.github.robwin;

import com.fasterxml.jackson.databind.Module;
import io.github.robwin.circuitbreaker.CircuitBreakerRegistry;
import io.github.robwin.circuitbreaker.event.CircuitBreakerEvent;
import io.github.robwin.config.CircuitBreakerProperties;
import io.github.robwin.consumer.CircularEventConsumer;
import io.github.robwin.monitoring.health.CircuitBreakerHealthIndicator;
import javaslang.jackson.datatype.JavaslangModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public HealthIndicator backendA(CircuitBreakerRegistry circuitBreakerRegistry,
									CircuitBreakerProperties circuitBreakerProperties,
									CircularEventConsumer<CircuitBreakerEvent> circuitBreakerEventConsumer){
		return new CircuitBreakerHealthIndicator(circuitBreakerRegistry, circuitBreakerProperties, circuitBreakerEventConsumer, "backendA");
	}

	@Bean
	public HealthIndicator backendB(CircuitBreakerRegistry circuitBreakerRegistry,
									CircuitBreakerProperties circuitBreakerProperties,
									CircularEventConsumer<CircuitBreakerEvent> circuitBreakerEventConsumer){
		return new CircuitBreakerHealthIndicator(circuitBreakerRegistry, circuitBreakerProperties, circuitBreakerEventConsumer, "backendB");
	}

	@Bean
	public CircuitBreakerRegistry circuitBreakerRegistry() {
		return CircuitBreakerRegistry.ofDefaults();
	}

	@Bean
	public CircularEventConsumer<CircuitBreakerEvent> circuitBreakerEventConsumer(CircuitBreakerProperties circuitBreakerProperties) {
		return new CircularEventConsumer<>(circuitBreakerProperties.getEventBufferSize());
	}

	@Bean
	public Module javaslangModule() {
		return new JavaslangModule();
	}
}
