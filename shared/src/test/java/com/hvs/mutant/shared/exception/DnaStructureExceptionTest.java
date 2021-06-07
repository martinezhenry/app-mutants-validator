package com.hvs.mutant.shared.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DnaStructureExceptionTest {
    private String message;

    @BeforeEach
    void setUp() {
        message = "This a Test";

    }

    @Test
    void buildThrow() {

        DnaStructureException dnaStructureException = new DnaStructureException(message);
        Assertions.assertNotNull(dnaStructureException);
        Assertions.assertEquals(message, dnaStructureException.getMessage());

    }
}