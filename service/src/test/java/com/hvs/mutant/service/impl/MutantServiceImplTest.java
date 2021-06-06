package com.hvs.mutant.service.impl;

import com.hvs.mutant.service.MutantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MutantServiceImpl.class)
class MutantServiceImplTest {

    @Autowired
    private MutantService mutantService;

    @Test
    void isMutant() {
    }

    @Test
    void validateHorizontalDna() {

        String[] dna = {"111156", "113456", "431556", "675156", "153457", "123478"};
        boolean result = mutantService.validateDna(dna);
        Assertions.assertTrue(result);

    }
}