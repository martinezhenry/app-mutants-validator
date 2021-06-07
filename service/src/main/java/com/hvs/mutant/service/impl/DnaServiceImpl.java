package com.hvs.mutant.service.impl;

import com.hvs.mutant.service.DnaService;
import com.hvs.mutant.shared.ErrorType;
import com.hvs.mutant.shared.config.AppConfig;
import com.hvs.mutant.shared.exception.DnaStructureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DnaServiceImpl implements DnaService {

    private Logger logger = LoggerFactory.getLogger(DnaServiceImpl.class);
    private AppConfig config;

    public DnaServiceImpl(AppConfig config) {
        this.config = config;
    }

    @Override
    public void validateSequenceData(char[] sequence, int dnaLength) {
        if (sequence.length != dnaLength) {
            throw new DnaStructureException(
                    String.format(config.getErrorMessages().get(ErrorType.SEQ_LENGTH_INVALID.name())
                            , Arrays.toString(sequence), sequence.length, dnaLength)
            );
        }

        String exp = "[A][T][C][D]";
        if (String.valueOf(sequence).matches(exp)) {
            logger.debug("matches");
        } else {
            logger.debug("not matches");
        }

    }


    @Override
    public char[][] buildDnaTable(String[] dna) {
        char[][] table = new char[dna.length][dna.length];

        for (int row = 0; row < dna.length; row++) {
            char[] rowData = dna[row].toCharArray();
            validateSequenceData(rowData, dna.length);
            table[row] = rowData;
        }


        return table;
    }
}
