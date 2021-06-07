package com.hvs.mutant.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.hvs.mutant")
@EnableMongoRepositories(basePackages = "com.hvs.mutant")
@EnableAsync
public class MutantApplication {

    /**
     * Main method to run application
     *
     * @param args Args is not already used
     */
    public static void main(String[] args) {
        SpringApplication.run(MutantApplication.class);
    }

}
