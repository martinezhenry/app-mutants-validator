package com.hvs.mutant.service.impl;

import com.hvs.mutant.model.Nucleotide;
import com.hvs.mutant.model.Sequence;
import com.hvs.mutant.model.SequenceType;
import com.hvs.mutant.service.SequenceService;
import com.hvs.mutant.shared.config.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {SequenceServiceImpl.class, AppConfig.class})
class SequenceServiceImplTest {

    @Autowired
    private SequenceService sequenceService;

    @Test
    void generateNucleotide() {
        char data = 'T';
        int xCoordinate = 0;
        int yCoordinate = 0;

        Nucleotide nucleotide = sequenceService.generateNucleotide(data, xCoordinate, yCoordinate);

        Assertions.assertNotNull(nucleotide);
        Assertions.assertEquals(data, nucleotide.getData());
        Assertions.assertEquals(xCoordinate, nucleotide.getXCoordinate());
        Assertions.assertEquals(yCoordinate, nucleotide.getYCoordinate());


    }

    @Test
    void generateSequence() {

        SequenceType type = SequenceType.HORIZONTAL;

        Sequence sequence = sequenceService.generateSequence(type);

        Assertions.assertNotNull(sequence);
        Assertions.assertNotNull(sequence.getNucleotides());
        Assertions.assertEquals(0, sequence.getNucleotides().size());
        Assertions.assertEquals(type, sequence.getType());

    }
}