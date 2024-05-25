package com.example.fortuneforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@EnableAsync
public class FortuneForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FortuneForgeApplication.class, args);
    }

}
