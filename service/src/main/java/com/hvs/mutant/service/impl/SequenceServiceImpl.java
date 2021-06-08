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

    /**
     * Build a Nucleotide instance from value, x & y position
     * @param data Value of nucleotide
     * @param xCoordinate X (Horizontal) position
     * @param yCoordinate Y (Vertical) position
     * @return Nucleotide instance
     */
    @Override
    public Nucleotide generateNucleotide(char data, int xCoordinate, int yCoordinate) {
        var nucleotide = new Nucleotide();
        nucleotide.setData(data);
        nucleotide.setXCoordinate(xCoordinate);
        nucleotide.setYCoordinate(yCoordinate);
        return nucleotide;
    }

    /**
     * Build Sequence instance from sequecne type
     * @param sequenceType to create instance
     * @return Sequence instance
     */
    @Override
    public Sequence generateSequence(SequenceType sequenceType) {
        var sequence = new Sequence();
        sequence.setType(sequenceType);
        return sequence;
    }

    /**
     * Validate if sequence size if equals to the expected
     * @param sequence Sequece to validate
     * @param validationData ValidationData instance to add sequence only if is completed
     * @return Sequence validated
     */
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

    /**
     * Validate if a Nucleotide is the same of last nucleotide in sequence param
     * @param nucleotide Nucleotide to validate
     * @param sequence Sequence with last nucleotide
     */
    public void validateSequence(Nucleotide nucleotide, Sequence sequence) {

        List<Nucleotide> nucleotides = sequence.getNucleotides();
        int nucleotidesSize = nucleotides.size();
        Nucleotide lastNucleotide = null;
        // checking if sequence size is not zero
        if (nucleotidesSize > 0) {
            // getting laste sequence
            lastNucleotide = nucleotides.get(nucleotidesSize - 1);
        }

        // if last sequence exist then go to comparate, else setter current nucleotide
        Optional.ofNullable(lastNucleotide).ifPresentOrElse(previos -> {

            // getting nucleotides to comparate
            char previosNucleotide = previos.getData();
            char currentNucleotide = nucleotide.getData();

            // comparate
            if (currentNucleotide == previosNucleotide) {
                // equals, add nucleotide to sequence
                logger.debug("nucleotides matched, {} = {}", previos, nucleotide);
                sequence.getNucleotides().add(nucleotide);
                logger.trace("current nucleotides: {}", sequence.getNucleotides());

            } else {
                // cleaning sequence, are not equals
                logger.debug("nucleotides NOT matched, {} = {}", previos, nucleotide);
                logger.trace("Clearing sequence: {}", sequence);
                sequence.setNucleotides(new ArrayList<>());
            }


        }, () -> {
            // setting current nucleotide, last nucleotide not exists
            logger.debug("last nucleotide is emtpy, setting current nucleotide: {}", nucleotide);
            sequence.getNucleotides().add(nucleotide);
        });


    }
}
