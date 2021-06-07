package com.hvs.mutant.service.impl;

import com.hvs.mutant.model.Sequence;
import com.hvs.mutant.model.Specimen;
import com.hvs.mutant.model.Stats;
import com.hvs.mutant.repository.SpecimenRepository;
import com.hvs.mutant.repository.StatsRepository;
import com.hvs.mutant.service.PersistenceService;
import com.hvs.mutant.shared.config.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PersistenceServiceImpl.class, AppConfig.class})
class PersistenceServiceImplTest {

    @Autowired
    private PersistenceService persistenceService;

    @MockBean
    private SpecimenRepository specimenRepository;

    @MockBean
    private StatsRepository statsRepository;

    private Specimen specimen;
    private Stats stats;
    private String id = "TestId";

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(persistenceService);
        specimen = new Specimen();

        stats = new Stats(id, 0, 0, 0.0);

        Mockito.when(specimenRepository.save(Mockito.any())).thenReturn(specimen);
        Mockito.when(statsRepository.findById(Mockito.any())).thenReturn(Optional.of(stats));
    }

    @Test
    void saveSpecimen() {

        Sequence sequence = new Sequence();
        List<Sequence> sequences = new ArrayList<>();
        sequences.add(sequence);
        specimen.setMutant(true);
        specimen.setSequences(sequences);
        Assertions.assertDoesNotThrow(() -> persistenceService.saveSpecimen(specimen));



    }



    @Test
    void findStats() {

        Stats stats = persistenceService.findStats();
        Assertions.assertNotNull(stats);

    }

    @Test
    void calculateStats() {
    }
}