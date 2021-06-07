package com.hvs.mutant.service.impl;

import com.hvs.mutant.model.Stats;
import com.hvs.mutant.repository.SpecimenRepository;
import com.hvs.mutant.repository.StatsRepository;
import com.hvs.mutant.service.StatsService;
import com.hvs.mutant.shared.config.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {StatsServiceImpl.class, PersistenceServiceImpl.class, AppConfig.class})
class StatsServiceImplTest {


    @Autowired
    private StatsService statsService;


    @MockBean
    private SpecimenRepository specimenRepository;

    @MockBean
    private StatsRepository statsRepository;

    @BeforeEach
    void setUp() {

        Assertions.assertNotNull(statsService);


    }

    @Test
    void getStats() {

        Stats stats = statsService.getStats();
        Assertions.assertNotNull(stats);


    }
}