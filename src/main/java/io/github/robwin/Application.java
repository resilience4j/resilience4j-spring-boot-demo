package io.github.robwin;


import com.codahale.metrics.MetricRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.monitoring.health.CircuitBreakerHealthIndicator;
import io.github.resilience4j.consumer.EventConsumerRegistry;
import io.github.resilience4j.metrics.CircuitBreakerMetrics;
import io.github.resilience4j.prometheus.CircuitBreakerExports;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;

@SpringBootApplication
@EnableSpringBootMetricsCollector
@EnablePrometheusEndpoint
@EnableConfigurationProperties
public class Application {

	@Autowired
	MetricRegistry metricRegistry;

	@Autowired
	CircuitBreakerRegistry circuitBreakerRegistry;

	@Autowired

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

	@PostConstruct
	public void registerCircuitBreakerMetrics(){
		metricRegistry.registerAll(CircuitBreakerMetrics.of(circuitBreakerRegistry));
		CollectorRegistry.defaultRegistry.register(CircuitBreakerExports.ofCircuitBreakerRegistry(circuitBreakerRegistry));
	}
}
