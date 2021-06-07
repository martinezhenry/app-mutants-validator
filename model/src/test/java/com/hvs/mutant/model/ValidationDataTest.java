package com.hvs.mutant.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ValidationDataTest {

    private ValidationData validationData;

    @BeforeEach
    void setUp() {
        validationData = new ValidationData();
    }

    @Test
    void getSequences() {
        List<Sequence> sequences = new ArrayList<>();
        Sequence sequence = new Sequence();
        sequence.setType(SequenceType.VERTICAL);
        sequences.add(sequence);
        Assertions.assertNotNull(validationData.getSequences());
        Assertions.assertEquals(0, validationData.getSequences().size());

    }
}