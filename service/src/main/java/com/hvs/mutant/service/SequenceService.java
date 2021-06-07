package com.hvs.mutant.service;

import com.hvs.mutant.model.Nucleotide;
import com.hvs.mutant.model.Sequence;
import com.hvs.mutant.model.SequenceType;
import com.hvs.mutant.model.ValidationData;

public interface SequenceService {


    Nucleotide generateNucleotide(char data, int xCoordinate, int yCoordinate);

    Sequence generateSequence(SequenceType sequenceType);

    Sequence isComplete(Sequence sequence, ValidationData validationData);

    void validateSequence(Nucleotide nucleotide, Sequence sequence);

}
