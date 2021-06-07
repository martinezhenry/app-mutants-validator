package com.hvs.mutant.service;

public interface DnaService {


    void validateSequenceData(char[] sequence, int dnaLength);

    char[][] buildDnaTable(String[] dna);


}
