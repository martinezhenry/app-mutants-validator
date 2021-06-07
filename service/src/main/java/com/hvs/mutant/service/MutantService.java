package com.hvs.mutant.service;


import com.hvs.mutant.model.Response;
import com.hvs.mutant.model.Specimen;

public interface MutantService {


    Response validate(Specimen specimen);

    boolean isMutant(String[] dna);

}
