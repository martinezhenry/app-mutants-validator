package com.hvs.mutant.controller.impl;

import com.hvs.mutant.controller.StatsController;
import com.hvs.mutant.model.Stats;
import com.hvs.mutant.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsControllerImpl implements StatsController {

    private Logger logger = LoggerFactory.getLogger(StatsControllerImpl.class);

    private StatsService statsService;

    public StatsControllerImpl(StatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stats stats() {
        logger.debug("running statistics method");
        return statsService.getStats();
    }
}
