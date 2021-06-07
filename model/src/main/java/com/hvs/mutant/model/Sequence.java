package com.hvs.mutant.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model to represent DNA's sequences
 */
@Data
public class Sequence {

    private List<Nucleotide> nucleotides;
    private SequenceType type;


    public Sequence() {
        // initializing list of nucleotides
        nucleotides = new ArrayList<>();
    }

}
