package com.hvs.mutant.shared.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config")
@Data
public class AppConfig {

    private int minSeqForMutant;
    private int nucleotidesForSequence;
    private int statusToMutant;
    private int statusToNotMutant;
    private boolean debug;
    //private String statsId;

    private HashMap<String, String> errorMessages;


}
