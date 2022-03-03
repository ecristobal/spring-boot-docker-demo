package com.example.spring.boot.docker.demo.tracing;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class DistributedTracingConfiguration {

    @Bean
    CorrelationRequestPropagator correlationRequestPropagator() {
        return new CorrelationRequestPropagator();
    }

    @Bean
    WebFilter traceIdInResponseFilter(Tracer tracer) {
        return (exchange, chain) -> {
            final Span currentSpan = tracer.currentSpan();
            if(currentSpan != null) {
                exchange.getResponse().getHeaders().add("X-Correlation-Id", currentSpan.context().traceId());
                exchange.getResponse().getHeaders().add("X-Request-Id", currentSpan.context().spanId());
            }
            return chain.filter(exchange);
        };
    }

}
