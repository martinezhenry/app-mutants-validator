package com.hvs.mutant.controller.impl;

import com.hvs.mutant.controller.MutantController;
import com.hvs.mutant.entity.Dna;
import com.hvs.mutant.entity.Stats;
import com.hvs.mutant.service.MutantService;
import com.hvs.mutant.shared.exception.NotMutantException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MutantControllerImpl implements MutantController {

    Logger logger = LoggerFactory.getLogger(MutantControllerImpl.class);
    private MutantService mutantService;

    public MutantControllerImpl(MutantService mutantService) {
        this.mutantService = mutantService;
    }


    @Override
    @PostMapping(value = {"/mutant"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void mutant(@RequestBody Dna dna) {
        logger.debug("running mutant method");
        if (!mutantService.isMutant(dna)) {
            throw new NotMutantException();
        }
    }

    @Override
    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stats stats() {
        logger.debug("running statistics method");
        return null;
    }
}
