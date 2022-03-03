package com.example.spring.boot.docker.demo;

import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootDockerDemoApplication.class);

    @GetMapping("/factorial")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BigInteger> factorial(final @RequestParam int number) {
        BigInteger factorial = BigInteger.ONE;
        for(int i = 2; i <= number; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        LOGGER.info("Factorial for number {} is {}", number, factorial);
        return Mono.just(factorial);
    }

}
