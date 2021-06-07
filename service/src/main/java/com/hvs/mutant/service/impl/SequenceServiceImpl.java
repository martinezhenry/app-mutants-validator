package com.hvs.mutant.service.impl;

import com.hvs.mutant.model.Nucleotide;
import com.hvs.mutant.model.Sequence;
import com.hvs.mutant.model.SequenceType;
import com.hvs.mutant.model.ValidationData;
import com.hvs.mutant.service.SequenceService;
import com.hvs.mutant.shared.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SequenceServiceImpl implements SequenceService {

    private Logger logger = LoggerFactory.getLogger(SequenceServiceImpl.class);
    private AppConfig config;

    public SequenceServiceImpl(AppConfig config) {
        this.config = config;

    }

    @Override
    public Nucleotide generateNucleotide(char data, int xCoordinate, int yCoordinate) {
        Nucleotide nucleotide = new Nucleotide();
        nucleotide.setData(data);
        nucleotide.setXCoordinate(xCoordinate);
        nucleotide.setYCoordinate(yCoordinate);
        return nucleotide;
    }

    @Override
    public Sequence generateSequence(SequenceType sequenceType) {
        Sequence sequence = new Sequence();
        sequence.setType(sequenceType);
        return sequence;
    }

    @Override
    public Sequence isComplete(Sequence sequence, ValidationData validationData) {
        if (sequence.getNucleotides().size() == config.getNucleotidesForSequence()) {
            logger.trace("sequence detected, including data.");
            validationData.getSequences().add(sequence);
            logger.trace("restarting sequence");
            return this.generateSequence(sequence.getType());
        }
        return sequence;
    }


    public void validateSequence(Nucleotide nucleotide, Sequence sequence) {

        List<Nucleotide> nucleotides = sequence.getNucleotides();
        int nucleotidesSize = nucleotides.size();
        Nucleotide lastNucleotide = null;
        if (nucleotidesSize > 0) {
            lastNucleotide = nucleotides.get(nucleotidesSize - 1);
        }

        Optional.ofNullable(lastNucleotide).ifPresentOrElse(previos -> {

            char previosNucleotide = previos.getData();
            char currentNucleotide = nucleotide.getData();

            if (currentNucleotide == previosNucleotide) {
                logger.debug("nucleotides matched, {} = {}", previos, nucleotide);
                sequence.getNucleotides().add(nucleotide);
                logger.trace("current nucleotides: {}", sequence.getNucleotides());

            } else {
                logger.debug("nucleotides NOT matched, {} = {}", previos, nucleotide);
                logger.trace("Clearing sequence: {}", sequence);
                sequence.setNucleotides(new ArrayList<>());
            }


        }, () -> {
            logger.debug("last nucleotide is emtpy, setting current nucleotide: {}", nucleotide);
            sequence.getNucleotides().add(nucleotide);
        });


    }
}
