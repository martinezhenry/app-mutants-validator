package com.hvs.mutant.controller;

import com.hvs.mutant.entity.Dna;
import com.hvs.mutant.entity.Stats;

public interface MutantController {

    void mutant(Dna dna);

    Stats stats();
}
