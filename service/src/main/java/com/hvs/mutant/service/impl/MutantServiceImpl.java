package com.hvs.mutant.service.impl;

import com.hvs.mutant.model.Nucleotide;
import com.hvs.mutant.model.Sequence;
import com.hvs.mutant.model.ValidationData;
import com.hvs.mutant.service.MutantService;
import com.hvs.mutant.shared.SequenceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MutantServiceImpl implements MutantService {

    Logger logger = LoggerFactory.getLogger(MutantServiceImpl.class);

    @Override
    public boolean isMutant(String[] dna) {
        logger.debug("checking dna");
        if (dna.length == 0) {
            return false;
        }

        for (String subDna : dna){

        }

        return true;
    }



    public boolean validateDna(String[] dna) {
        ValidationData validationData = new ValidationData();
        Sequence sequenceHorizontal = new Sequence();
        Sequence sequenceVertical = new Sequence();
        Sequence sequenceOblique = new Sequence();
        Sequence sequenceReverseOblique = new Sequence();

        char[][] table = buildDnaTable(dna);
        printDnaTable(table);
        char[] nucleotidesRow;

        for (int row = 0; row < table.length; row++) {

            nucleotidesRow = table[row];
            Nucleotide nucleotide;

            logger.debug("---------------- Row {} ----------------", row+1);

            // validacion horizontal
            for (int col = 0; col < nucleotidesRow.length; col++) {

                /************** Horizontal ******************/
                nucleotide = generateNucleotide(table[row][col], row, col);
                validateSequence(nucleotide, sequenceHorizontal);
                sequenceComplete(sequenceHorizontal, validationData);
                //logger.debug("Horizontal. data[row:{}, column:{}, value: {}, count: {}]", row+1, col+1, nucleotide, validationData.getCountH());


                /************** Vertical ******************/
                nucleotide = generateNucleotide(table[col][row], col, row);

                validateSequence(nucleotide, sequenceVertical);
                sequenceComplete(sequenceVertical, validationData);

                //validateSequence(nucleotide, validationData, 2);
                //logger.debug("Vertical. data[row:{}, column:{}, value: {}, count: {}]", col+1, row+1, nucleotide, validationData.getCountV());


                /************** Oblicua ******************/
                boolean breakO = false;
                boolean breakI = false;
                for (int i = 0, j = table.length; i < table.length; i++, j--) {
                    int tmpRow = row+i;
                    int tmpCol = col+i;

                    int tmpRow2 = row-j;
                    int tmpCol2 = col-i;


                    if (!breakO) {
                        if (tmpRow < table.length && tmpCol < table[row].length) {
                            //nucleotide = table[tmpRow][tmpCol];
                            nucleotide = generateNucleotide(table[tmpRow][tmpCol], tmpRow, tmpCol);
                            validateSequence(nucleotide, sequenceOblique);
                            sequenceComplete(sequenceOblique, validationData);
                            //validateSequence(nucleotide, validationData, SequenceType.OBLIQUE);
                            //logger.debug("Oblicua. data[row:{}, column:{}, value: {}, count: {}]", tmpRow + 1, tmpCol + 1, nucleotide, validationData.getCountO());
                        } else {
                            //validationData.setSequenceV("");
                            //logger.debug("oversize of table, max len to row & col is {}. Current position [{}][{}]", table.length, tmpRow + 1, tmpCol + 1);
                            //break;
                            breakO = true;
                        }
                    }

                    if (!breakI) {
                        if (tmpCol2 >= 0 && tmpRow < table.length) {
                            //nucleotide = table[tmpRow][tmpCol2];
                            nucleotide = generateNucleotide(table[tmpRow][tmpCol2], tmpRow, tmpCol2);

                            //validateSequence(nucleotide, validationData, SequenceType.REVERSE_OBLIQUE);
                            validateSequence(nucleotide, sequenceReverseOblique);
                            sequenceComplete(sequenceReverseOblique, validationData);
                            logger.debug("Inversa Oblicua. data[row:{}, column:{}, value: {}, sequence: {}]", tmpRow + 1, tmpCol2 + 1, nucleotide, sequenceReverseOblique);
                        } else {
                            //validationData.setSequenceI("");
                            logger.debug("Inversa oversize of table, min len to row & col is {}. Current position [{}][{}]", 0, tmpRow + 1, tmpCol2 - 1);
                            //break;
                            breakI = true;
                        }
                    }


                    if (breakI && breakO) {
                        break;
                    }

                }




            }


        }


        logger.debug("total sequences: {}", validationData.getSequences());
        return (validationData.getSequences().size() > 1);
    }


    public void sequenceComplete(Sequence sequence, ValidationData validationData){
        if (sequence.getNucleotides().size() == 4) {
            validationData.getSequences().add(sequence);
            sequence = new Sequence();
        }
    }

    public Nucleotide generateNucleotide(char data, int xCoordinate, int yCoordinate){
        Nucleotide nucleotide = new Nucleotide();
        nucleotide.setData(data);
        nucleotide.setXCoordinate(xCoordinate);
        nucleotide.setYCoordinate(yCoordinate);
        return nucleotide;
    }


    public void validateSequence(Nucleotide nucleotide, Sequence sequence){

        List<Nucleotide> nucleotides = sequence.getNucleotides();
        int nucleotidesSize = nucleotides.size();
        Nucleotide lastNucleotide = nucleotides.get(nucleotidesSize - 1);

        Optional.ofNullable(lastNucleotide).ifPresentOrElse(previos -> {

            char previosNucleotide = previos.getData();
            char currentNucleotide = nucleotide.getData();

            if (currentNucleotide == previosNucleotide) {
                sequence.getNucleotides().add(nucleotide);

                logger.debug("current nucleotides: {}", sequence.getNucleotides());
                if (sequence.getNucleotides().size() == 4) {
                    logger.debug("sequence completed, type: {}, current nucleotides: {}", sequence.getType(), sequence.getNucleotides());
                }
            } else {
                sequence.setNucleotides(new ArrayList<>());
                //seq = "";
                //previosValue = '\u0000';
            }
            //previosNucleotide = currentNucleotide;


        }, () -> {
            sequence.getNucleotides().add(nucleotide);
        });



    }



    public char[][] buildDnaTable(String [] dna){
        char[][] table = new char[dna.length][dna.length];
        for(int row = 0; row < dna.length; row++){
            char[] rowData = dna[row].toCharArray();
            table[row] = rowData;
        }


        return table;
    }


    public void printDnaTable(char[][] table){
        StringBuilder line = new StringBuilder("| ");
        for(int row = 0; row < table.length; row++){
            for(int col = 0; col < table[row].length; col++) {
                line.append(table[row][col]).append(" ");
            }
            line.append("|");
            logger.debug("{}", line);
            line = new StringBuilder("| ");
        }
    }


}
