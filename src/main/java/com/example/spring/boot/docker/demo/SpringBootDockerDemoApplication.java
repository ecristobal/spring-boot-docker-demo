package com.example.spring.boot.docker.demo;

import com.example.spring.boot.docker.demo.tracing.DistributedTracingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = DistributedTracingConfiguration.class)
public class SpringBootDockerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDockerDemoApplication.class, args);
    }

}
