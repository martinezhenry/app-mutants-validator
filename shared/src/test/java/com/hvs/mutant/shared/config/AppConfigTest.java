package com.hvs.mutant.shared.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@SpringBootTest(classes = {AppConfig.class})
@EnableAutoConfiguration
class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @Value("${config.status-to-mutant}")
    int defaultStatusMutant;
    @Value("${config.status-to-not-mutant}")
    int defaultStatusNotMutant;
    @Value("${config.debug}")
    boolean defaultDebug;
    @Value("${config.stats-id}")
    String defaultStatsId;


    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(appConfig);

    }

    @Test
    void minSeqForMutant() {
        Assertions.assertNotEquals(0, appConfig.getMinSeqForMutant());
        appConfig.setMinSeqForMutant(10);
        Assertions.assertEquals(10, appConfig.getMinSeqForMutant());
    }

    @Test
    void getNucleotidesForSequence() {
        Assertions.assertNotEquals(0, appConfig.getNucleotidesForSequence());
        appConfig.setNucleotidesForSequence(10);
        Assertions.assertEquals(10, appConfig.getNucleotidesForSequence());
    }

    @Test
    void getStatusToMutant() {
        Assertions.assertEquals(defaultStatusMutant, appConfig.getStatusToMutant());
        appConfig.setStatusToMutant(HttpStatus.ACCEPTED.value());
        Assertions.assertEquals(HttpStatus.ACCEPTED.value(), appConfig.getStatusToMutant());
    }

    @Test
    void getStatusToNotMutant() {
        Assertions.assertEquals(defaultStatusNotMutant, appConfig.getStatusToNotMutant());
        appConfig.setStatusToNotMutant(HttpStatus.NOT_FOUND.value());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), appConfig.getStatusToNotMutant());
    }

    @Test
    void isDebug() {
        Assertions.assertEquals(defaultDebug, appConfig.isDebug());
        appConfig.setDebug(true);
        Assertions.assertTrue(appConfig.isDebug());
    }


    @Test
    void getErrorMessages() {
        Assertions.assertNotNull(appConfig.getErrorMessages());
        Assertions.assertNotEquals(0, appConfig.getErrorMessages().size());
        HashMap<String, String> errors = new HashMap();
        String message = "This is a Test";
        String key = "TEST";
        errors.put(key, message);
        appConfig.setErrorMessages(errors);
        Assertions.assertEquals(errors.size(), appConfig.getErrorMessages().size());
        Assertions.assertEquals(message, appConfig.getErrorMessages().get(key));
    }

}