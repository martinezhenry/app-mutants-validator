package com.hvs.mutant.service.impl;

import com.hvs.mutant.model.Specimen;
import com.hvs.mutant.model.Stats;
import com.hvs.mutant.repository.SpecimenRepository;
import com.hvs.mutant.repository.StatsRepository;
import com.hvs.mutant.service.PersistenceService;
import com.hvs.mutant.shared.config.AppConfig;
import com.hvs.mutant.shared.util.MutantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PersistenceServiceImpl implements PersistenceService {


    private Logger logger = LoggerFactory.getLogger(PersistenceServiceImpl.class);
    private SpecimenRepository specimenRepository;
    private StatsRepository statsRepository;
    private AppConfig config;

    public PersistenceServiceImpl(SpecimenRepository specimenRepository, StatsRepository statsRepository, AppConfig config) {
        this.specimenRepository = specimenRepository;
        this.statsRepository = statsRepository;
        this.config = config;
    }


    /**
     * Async method to save specimen data in database & update stats
     * @param specimen Specimen to save
     */
    @Override
    @Async
    public void saveSpecimen(Specimen specimen) {

        logger.trace("saving specimen: {}", specimen);

        specimen.setId(specimen.hashCode());
        specimen = specimenRepository.save(specimen);
        logger.debug("saved specimen: {}", specimen);

        var stats = statsRepository.findById(STATS_ID).orElse(new Stats(STATS_ID, 0, 0, 0.0));
        logger.debug("stats: {}", stats);

        logger.trace("calculating stats");
        stats = calculateStats(stats, specimen.isMutant());
        logger.debug("stats calculated: {}", stats);

        stats = saveStats(stats);
        logger.debug("saved stats: {}", stats);

    }

    /**
     * Method to save Stats in database
     * @param stats Stats to save
     * @return Stats after the save
     */
    @Override
    public Stats saveStats(Stats stats) {
        logger.trace("saving stats: {}", stats);
        return statsRepository.save(stats);
    }

    /**
     * Get stats from database
     * @return Stats obtained
     */
    @Override
    public Stats findStats() {
        return statsRepository.findById(STATS_ID).orElse(new Stats(STATS_ID, 0, 0, 0.0));
    }

    /**
     * Update stats depent of mutant param, if mutant is true
     * count mutant increment, else count human increment
     * @param stats Stats to comparte & update
     * @param mutant boolean to indicate if is a mutant or not
     * @return Stats updated
     */
    @Override
    public Stats calculateStats(Stats stats, boolean mutant) {

        double mutants = stats.getCountMutantDna();
        int humans = stats.getCountHumanDna();
        double ratio = 0.0;

        if (mutant) {
            mutants++;
        } else {
            humans++;
        }
        stats.setCountMutantDna((int) mutants);
        stats.setCountHumanDna(humans);

        if (humans > 0) {
            ratio = (mutants / humans);
        }
        stats.setRatio(MutantUtil.formatDouble(ratio, "#.#"));

        return stats;
    }


}
