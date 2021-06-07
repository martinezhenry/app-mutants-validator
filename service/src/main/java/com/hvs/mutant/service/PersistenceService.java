package com.hvs.mutant.service;

import com.hvs.mutant.model.Specimen;
import com.hvs.mutant.model.Stats;

public interface PersistenceService {

    String STATS_ID = "stats.id";

    void saveSpecimen(Specimen specimen);

    Stats calculateStats(Stats stats, boolean mutant);

    Stats saveStats(Stats stats);

    Stats findStats();

}
