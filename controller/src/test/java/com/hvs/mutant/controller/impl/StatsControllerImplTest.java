package com.hvs.mutant.controller.impl;

import com.hvs.mutant.controller.StatsController;
import com.hvs.mutant.model.Stats;
import com.hvs.mutant.service.PersistenceService;
import com.hvs.mutant.service.StatsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {StatsControllerImpl.class})
class StatsControllerImplTest {

    @Autowired
    private StatsController statsController;

    @MockBean
    private StatsService statsService;

    private Stats stats;

    @BeforeEach
    void setUp() {
        stats = new Stats(PersistenceService.STATS_ID, 0, 0, 0.0);

    }

    /**
     * Test to query stats
     */
    @Test
    void stats() {

        Mockito.when(statsService.getStats()).thenReturn(stats);
        Assertions.assertNotNull(statsController);
        Assertions.assertDoesNotThrow(() -> statsController.stats());
        Stats stats = statsController.stats();
        Assertions.assertNotNull(stats);

    }
}
