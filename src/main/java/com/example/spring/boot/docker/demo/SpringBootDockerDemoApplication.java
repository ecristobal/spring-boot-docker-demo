package com.example.spring.boot.docker.demo;

import java.math.BigInteger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class SpringBootDockerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDockerDemoApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        //@formatter:off
        return RouterFunctions.route()
                .GET("/factorial", request -> {
                    final int number = Integer.valueOf(request.queryParam("number").get());
                    BigInteger factorial = BigInteger.ONE;
                    for (int i = 2; i <= number; i++)
                        factorial = factorial.multiply(BigInteger.valueOf(i));
                    return ServerResponse.ok().bodyValue(factorial);
                })
            .build();
        //@formatter:on
    }

}
