package com.hvs.mutant.controller;

import com.hvs.mutant.model.Specimen;
import com.hvs.mutant.model.Stats;

public interface MutantController {

    void mutant(Specimen specimen);

    Stats stats();
}
