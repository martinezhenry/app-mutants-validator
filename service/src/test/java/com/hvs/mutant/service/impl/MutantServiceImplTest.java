package com.hvs.mutant.service.impl;

import com.hvs.mutant.model.Response;
import com.hvs.mutant.model.Specimen;
import com.hvs.mutant.model.Stats;
import com.hvs.mutant.repository.SpecimenRepository;
import com.hvs.mutant.repository.StatsRepository;
import com.hvs.mutant.service.MutantService;
import com.hvs.mutant.shared.config.AppConfig;
import com.hvs.mutant.shared.exception.DnaStructureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@SpringBootTest(classes = {MutantServiceImpl.class, AppConfig.class, SequenceServiceImpl.class, DnaServiceImpl.class, PersistenceServiceImpl.class})
class MutantServiceImplTest {

    @Autowired
    private MutantService mutantService;
    @Autowired
    private AppConfig config;

    private Specimen specimen;
    private Stats stats;
    private final String id = "TestId";

    @MockBean
    private SpecimenRepository specimenRepository;
    @MockBean
    private StatsRepository statsRepository;


    @BeforeEach
    void setUp() {
        String[] dna = new String[]{"AAAA", "ATGA", "AATG", "ATGA"};
        specimen = new Specimen();
        specimen.setDna(dna);

        stats = new Stats(id, 0, 0, 0.0);

        Mockito.when(specimenRepository.save(Mockito.any())).thenReturn(specimen);
        Mockito.when(statsRepository.save(Mockito.any())).thenReturn(stats);
        Mockito.when(statsRepository.findById(Mockito.any())).thenReturn(Optional.of(stats));
    }


    @Test
    void validateDna() {



        Response response = mutantService.validate(specimen);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getSpecimen());
        Assertions.assertTrue(response.getSpecimen().isMutant());
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());


    }


    @Test
    void validateNotMutantDna() {

        String[] dna = new String[]{"ATGA", "GTGA", "GATG", "ATGA"};
        specimen.setDna(dna);
        Response response = mutantService.validate(specimen);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getSpecimen());
        Assertions.assertFalse(response.getSpecimen().isMutant());
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());


    }

    @Test
    void InvalidDna() {


        String[] dna = new String[]{"AAAAA", "ATGA", "AATG", "ATGA"};
        specimen.setDna(dna);
        Assertions.assertThrows(DnaStructureException.class,
                () -> mutantService.validate(specimen));


    }


}