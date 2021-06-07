package com.hvs.mutant.service.impl;

import com.hvs.mutant.model.*;
import com.hvs.mutant.service.DnaService;
import com.hvs.mutant.service.MutantService;
import com.hvs.mutant.service.PersistenceService;
import com.hvs.mutant.service.SequenceService;
import com.hvs.mutant.shared.config.AppConfig;
import com.hvs.mutant.shared.exception.NotMutantException;
import com.hvs.mutant.shared.util.MutantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MutantServiceImpl implements MutantService {

    private Logger logger = LoggerFactory.getLogger(MutantServiceImpl.class);
    private AppConfig config;
    private SequenceService sequenceService;
    private DnaService dnaService;
    private PersistenceService persistenceService;

    /***
     * Constructor with dependencies inject
     * @param config
     * @param sequenceService
     * @param dnaService
     * @param persistenceService
     */
    public MutantServiceImpl(AppConfig config, SequenceService sequenceService, DnaService dnaService, PersistenceService persistenceService) {
        this.config = config;
        this.sequenceService = sequenceService;
        this.dnaService = dnaService;
        this.persistenceService = persistenceService;
    }


    /**
     * Validate a specimen & build Response object
     * @param specimen Specimen with DNA array
     * @return Response with validation data
     */
    @Override
    public Response validate(Specimen specimen) {
        // detecting if is a mutant DNA
        boolean mutant = isMutant(specimen.getDna());

        // setting result
        specimen.setMutant(mutant);

        // build status from mutant result
        HttpStatus status = (mutant) ? HttpStatus.valueOf(config.getStatusToMutant()) : HttpStatus.valueOf(config.getStatusToNotMutant());

        // Building response
        var response = MutantUtil.buildResponse(status.name(), status.value(), specimen, null);

        // saving result in database
        persistenceService.saveSpecimen(specimen);

        // validating mutant to throw NotMutantException if is not a mutant DNA
        if (!Optional.ofNullable(response.getSpecimen()).orElse(specimen).isMutant()) {
            logger.debug("specimen is not a mutant");
            throw new NotMutantException(response.getMessage(), response.getSpecimen());
        }
        return response;
    }

    /**
     * Validate DNA to detect when a human is a mutant
     *
     * @param dna DNA to validate
     * @return true: Mutant / false: not Mutant
     */
    public boolean isMutant(String[] dna) {
        ValidationData validationData = new ValidationData();
        Sequence sequenceHorizontal;
        Sequence sequenceVertical;
        Sequence sequenceOblique;
        Sequence sequenceReverseOblique;

        // building table of dna
        char[][] table = dnaService.buildDnaTable(dna);

        // print table to log
        MutantUtil.printDnaTable(table, logger);

        char[] nucleotidesRow;

        // looping dna table
        for (int row = 0; row < table.length; row++) {

            /*
             * Initializing Horizontal/Vertical variables
             */
            sequenceHorizontal = sequenceService.generateSequence(SequenceType.HORIZONTAL);
            sequenceVertical = sequenceService.generateSequence(SequenceType.VERTICAL);

            nucleotidesRow = table[row];
            Nucleotide nucleotide;

            logger.trace("---------------- Row {} ----------------", row + 1);

            for (int col = 0; col < nucleotidesRow.length; col++) {

                /* ****************************************************************************
                 *  HORIZONTAL SECTION
                 *  checking if exists a mutant sequence in Horizontal way
                 * ****************************************************************************/
                logger.trace("---------------------------------------------------------------");
                logger.trace("--- horizontal validation -------------------------------------");
                logger.trace("Horizontal Sequence: {}", sequenceHorizontal);
                // building nucleotide object
                nucleotide = sequenceService.generateNucleotide(table[row][col], row, col);
                // validating nucleotide in sequence horizontal
                sequenceService.validateSequence(nucleotide, sequenceHorizontal);
                // validating if sequence is completed
                sequenceHorizontal = sequenceService.isComplete(sequenceHorizontal, validationData);


                /* ****************************************************************************
                 *  VERTICAL SECTION
                 * checking if exists a mutant sequence in Vertical way
                 * ****************************************************************************/
                logger.trace("---------------------------------------------------------------");
                logger.trace("--- vertical validation -------------------------------------");
                logger.trace("Vertical Sequence: {}", sequenceVertical);
                // building nucleotide object
                nucleotide = sequenceService.generateNucleotide(table[col][row], col, row);
                // validating nucleotide in sequence vertical
                sequenceService.validateSequence(nucleotide, sequenceVertical);
                // validating if sequence is completed
                sequenceVertical = sequenceService.isComplete(sequenceVertical, validationData);

                /* ****************************************************************************
                 *  OBLIQUES SECTION
                 * checking if exists a mutant sequence in Oblique way
                 * ****************************************************************************/

                /*
                 * Initializing Obliques variables
                 */

                boolean breakO = false;
                boolean breakI = false;
                sequenceOblique = sequenceService.generateSequence(SequenceType.OBLIQUE);
                sequenceReverseOblique = sequenceService.generateSequence(SequenceType.REVERSE_OBLIQUE);

                logger.trace("--- searching oblique sequences -------------------------------------");
                for (int i = 0, j = table.length; i < table.length; i++, j--) {

                    int oRow = row + i;
                    int oCol = col + i;

                    int rRow = oRow;
                    int rCol = col - i;


                    // checking if exists a mutant sequence in Oblique way (up to down)
                    if (!breakO) {
                        if (oRow < table.length && oCol < table[row].length) {
                            //nucleotide = table[tmpRow][tmpCol];
                            logger.trace("---------------------------------------------------------------");
                            logger.trace("--- oblique validation -------------------------------------");
                            logger.trace("Oblique Sequence: {}", sequenceOblique);
                            nucleotide = sequenceService.generateNucleotide(table[oRow][oCol], oRow, oCol);
                            sequenceService.validateSequence(nucleotide, sequenceOblique);
                            sequenceOblique = sequenceService.isComplete(sequenceOblique, validationData);

                        } else {
                            sequenceOblique = sequenceService.generateSequence(SequenceType.OBLIQUE);
                            logger.trace("oversize of table, max len to row & col is {}. Current position [{}][{}]", table.length, oRow, oCol);
                            breakO = true;
                        }
                    }

                    // checking if exists a mutant sequence in Oblique way (down to up)
                    if (!breakI) {
                        if (rCol >= 0 && rRow < table.length) {
                            logger.trace("---------------------------------------------------------------");
                            logger.trace("--- reverse oblique validation -------------------------------------");
                            logger.trace("Reverse Oblique Sequence: {}", sequenceReverseOblique);
                            nucleotide = sequenceService.generateNucleotide(table[rRow][rCol], rRow, rCol);

                            //validateSequence(nucleotide, validationData, SequenceType.REVERSE_OBLIQUE);
                            sequenceService.validateSequence(nucleotide, sequenceReverseOblique);
                            sequenceReverseOblique = sequenceService.isComplete(sequenceReverseOblique, validationData);
                            logger.trace("Inversa Oblicua. data[row:{}, column:{}, value: {}, sequence: {}]", rRow, rCol, nucleotide, sequenceReverseOblique);
                        } else {
                            //validationData.setSequenceI("");
                            sequenceReverseOblique = sequenceService.generateSequence(SequenceType.REVERSE_OBLIQUE);
                            logger.trace("Inversa oversize of table, min len to row & col is {}. Current position [{}][{}]", 0, rRow, rCol);
                            //break;
                            breakI = true;
                        }
                    }


                    // if both obliques sequences is oversize table, breaking loop to current position
                    if (breakI && breakO) {
                        logger.trace("obliques oversize of table");
                        break;
                    }

                }

            }

        }


        logger.info("total sequences: size {}, data: {}", validationData.getSequences().size(), validationData.getSequences());
        // checking if sequences found are over the expected
        boolean mutant = (validationData.getSequences().size() >= config.getMinSeqForMutant());
        logger.info("isMutant?: {}", mutant);
        return mutant;
    }


}
