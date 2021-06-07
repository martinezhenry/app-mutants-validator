package com.hvs.mutant.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SpecimenTest {

    private Specimen specimen;

    @BeforeEach
    void setUp() {

        specimen = new Specimen();
        Assertions.assertNotNull(specimen);

    }

    @Test
    void getId() {
        long id = 1000;
        Assertions.assertEquals(0, specimen.getId());
        specimen.setId(id);
        Assertions.assertEquals(id, specimen.getId());

    }

    @Test
    void getDna() {
        String[] dna = new String[]{"ATG"};
        Assertions.assertNull(specimen.getDna());
        specimen.setDna(dna);
        Assertions.assertEquals(dna, specimen.getDna());
    }

    @Test
    void getSequences() {
        List<Sequence> sequences = new ArrayList<>();
        Sequence sequence = new Sequence();
        sequence.setType(SequenceType.VERTICAL);
        sequences.add(sequence);
        Assertions.assertNull(specimen.getSequences());
        specimen.setSequences(sequences);
        Assertions.assertEquals(sequences, specimen.getSequences());
    }

    @Test
    void isMutant() {
        Assertions.assertFalse(specimen.isMutant());
        specimen.setMutant(true);
        Assertions.assertTrue(specimen.isMutant());
    }
}