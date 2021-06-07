package com.hvs.mutant.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatsTest {

    private Stats stats;
    private String id;

    @BeforeEach
    void setUp() {
        id = "StatsId";
        stats = new Stats(id, 0, 0, 0.0);
        Assertions.assertNotNull(stats);
    }

    @Test
    void getId() {
        Assertions.assertEquals(id, stats.getId());
        id = "StatsIdTest";
        stats.setId(id);
        Assertions.assertEquals(id, stats.getId());
    }

    @Test
    void getCountMutantDna() {
        Assertions.assertEquals(0, stats.getCountMutantDna());
        stats.setCountMutantDna(10);
        Assertions.assertEquals(10, stats.getCountMutantDna());
    }

    @Test
    void getCountHumanDna() {
        Assertions.assertEquals(0, stats.getCountHumanDna());
        stats.setCountHumanDna(10);
        Assertions.assertEquals(10, stats.getCountHumanDna());
    }

    @Test
    void getRatio() {
        Assertions.assertEquals(0.0, stats.getRatio());
        stats.setRatio(10.12);
        Assertions.assertEquals(10.12, stats.getRatio());
    }
}