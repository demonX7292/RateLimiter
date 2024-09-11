package org.ratelimiterservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RateLimitingService {
    public static void main(String[] args) {
        SpringApplication.run(RateLimitingService.class, "Rate Limiting Service");
    }
}