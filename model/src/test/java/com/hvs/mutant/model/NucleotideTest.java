package com.hvs.mutant.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NucleotideTest {

    private Nucleotide nucleotide;

    @BeforeEach
    void setUp() {
        nucleotide = new Nucleotide();
        Assertions.assertNotNull(nucleotide);
    }

    @Test
    void data() {
        Assertions.assertEquals('\u0000', nucleotide.getData());
        nucleotide.setData('T');
        Assertions.assertEquals('T', nucleotide.getData());
    }

    @Test
    void xCoordinate() {
        Assertions.assertEquals(0, nucleotide.getXCoordinate());
        nucleotide.setXCoordinate(1);
        Assertions.assertEquals(1, nucleotide.getXCoordinate());
    }

    @Test
    void yCoordinate() {
        Assertions.assertEquals(0, nucleotide.getYCoordinate());
        nucleotide.setYCoordinate(1);
        Assertions.assertEquals(1, nucleotide.getYCoordinate());
    }
}