package com.hvs.app.service.impl;

import com.hvs.app.service.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MutantServiceImpl implements MutantService {

    Logger logger = LoggerFactory.getLogger(MutantServiceImpl.class);

    @Override
    public boolean isMutant(String[] dna) {
        logger.debug("checking dna");
        return false;
    }
}
