package com.hvs.mutant.service.impl;

import com.hvs.mutant.entity.Dna;
import com.hvs.mutant.service.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MutantServiceImpl implements MutantService {

    Logger logger = LoggerFactory.getLogger(MutantServiceImpl.class);

    @Override
    public boolean isMutant(Dna dna) {
        logger.debug("checking dna");
        if (dna.getDna().length == 0) {
            return false;
        }
        return true;
    }
}
