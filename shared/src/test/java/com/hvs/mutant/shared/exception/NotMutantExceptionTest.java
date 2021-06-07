package com.hvs.mutant.shared.exception;

import com.hvs.mutant.model.Specimen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotMutantExceptionTest {

    private String message;
    private Specimen specimen;
    private Specimen specimenTest;

    @BeforeEach
    void setUp() {
        specimen = new Specimen();
        message = "This a Test";
        specimenTest = new Specimen();
        specimenTest.setMutant(true);
        specimenTest.setDna(new String[]{"ATGAT", "ATGAT", "ATGAT", "ATGAT", "ATGAT"});

    }

    @Test
    void buildThrow() {

        NotMutantException notMutantException = new NotMutantException(message, specimen);
        Assertions.assertNotNull(notMutantException);
        Assertions.assertEquals(message, notMutantException.getMessage());
        Assertions.assertEquals(specimen, notMutantException.getSpecimen());

        notMutantException.setSpecimen(specimenTest);
        Assertions.assertEquals(specimenTest, notMutantException.getSpecimen());

    }
}
