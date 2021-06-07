package com.hvs.mutant.service.impl;

import com.hvs.mutant.service.DnaService;
import com.hvs.mutant.shared.ErrorType;
import com.hvs.mutant.shared.config.AppConfig;
import com.hvs.mutant.shared.exception.DnaStructureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DnaServiceImpl implements DnaService {

    private Logger logger = LoggerFactory.getLogger(DnaServiceImpl.class);
    private AppConfig config;

    public DnaServiceImpl(AppConfig config) {
        this.config = config;
    }


    /**
     * Validate the structure & content of a sequence
     * @param sequence
     * @param dnaLength
     */
    @Override
    public void validateSequenceData(char[] sequence, int dnaLength) {
        if (sequence.length != dnaLength) {
            throw new DnaStructureException(
                    String.format(config.getErrorMessages().get(ErrorType.SEQ_LENGTH_INVALID.name())
                            , Arrays.toString(sequence), sequence.length, dnaLength)
            );
        }

        String exp = config.getExpDnaContentValid();
        Pattern pat = Pattern.compile(exp);
        Matcher matcher = pat.matcher(String.valueOf(sequence));
        if (!matcher.matches()) {
            throw new DnaStructureException(
                    String.format(config.getErrorMessages().get(ErrorType.SEQ_CONTENT_INVALID.name())
                            , Arrays.toString(sequence)));
        }

    }


    /**
     * Build a char table from DNA array
     * @param dna Array of sequences
     * @return Table char[][] generated
     */
    @Override
    public char[][] buildDnaTable(String[] dna) {
        char[][] table = new char[dna.length][dna.length];

        // loop of dna
        for (var row = 0; row < dna.length; row++) {
            char[] rowData = dna[row].toCharArray();
            // validate structured & content
            validateSequenceData(rowData, dna.length);
            table[row] = rowData;
        }


        return table;
    }
}
