package com.example.spring.boot.docker.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.WebFilter;

@SpringBootApplication
public class SpringBootDockerDemoApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootDockerDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDockerDemoApplication.class, args);
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
