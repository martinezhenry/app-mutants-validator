package com.hvs.mutant.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hvs.app")
public class MutantApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutantApplication.class);
    }

}
