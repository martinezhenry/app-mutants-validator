package com.hvs.mutant.model;

import com.hvs.mutant.shared.SequenceType;
import lombok.Data;

import java.util.List;

@Data
public class Sequence {

    private List<Nucleotide> nucleotides;
    private SequenceType type;

}
