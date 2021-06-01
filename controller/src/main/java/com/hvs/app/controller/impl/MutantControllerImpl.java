package com.hvs.app.controller.impl;

import com.hvs.app.controller.MutantController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MutantControllerImpl implements MutantController {

    Logger logger = LoggerFactory.getLogger(MutantControllerImpl.class);

    @Override
    @PostMapping(value = "/mutants", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void mutant() {
        logger.debug("running mutant method");
    }

    @Override
    @GetMapping(value = "statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public void statistics() {
        logger.debug("running statistics method");
    }
}
