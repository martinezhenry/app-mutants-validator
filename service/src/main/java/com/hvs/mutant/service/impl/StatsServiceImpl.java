package com.hvs.mutant.service.impl;

import com.hvs.mutant.model.Stats;
import com.hvs.mutant.service.PersistenceService;
import com.hvs.mutant.service.StatsService;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    private PersistenceService persistenceService;

    public StatsServiceImpl(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public Stats getStats() {
        return persistenceService.findStats();
    }
}
