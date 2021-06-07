package com.hvs.mutant.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SequenceTest {

    private Sequence sequence;

    @BeforeEach
    void setUp() {
        sequence = new Sequence();
        Assertions.assertNotNull(sequence);
    }

    @Test
    void nucleotides() {
        List<Nucleotide> nucleotides = new ArrayList<>();
        nucleotides.add(new Nucleotide());
        Assertions.assertNotNull(sequence.getNucleotides());
        Assertions.assertEquals(0, sequence.getNucleotides().size());
        sequence.setNucleotides(nucleotides);
        Assertions.assertEquals(nucleotides, sequence.getNucleotides());

    }

    @Test
    void type() {
        SequenceType type = SequenceType.VERTICAL;
        Assertions.assertNull(sequence.getType());
        sequence.setType(type);
        Assertions.assertEquals(type, sequence.getType());
    }
}
