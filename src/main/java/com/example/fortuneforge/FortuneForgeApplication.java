package com.example.fortuneforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class FortuneForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FortuneForgeApplication.class, args);
    }

}
