package com.hvs.mutant.controller.impl;

import com.hvs.mutant.controller.MutantController;
import com.hvs.mutant.model.Response;
import com.hvs.mutant.model.Specimen;
import com.hvs.mutant.service.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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


    /**
     * Method to expose POST service to validate DNA of a Specimen
     *
     * @param specimen Specimen with DNA to validate
     * @return Response Json with the validation result
     */
    @Override
    @PostMapping(value = {"/mutant"}, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response mutant(@RequestBody Specimen specimen) {

        logger.debug("running mutant method");
        // call to mutant service to validate DNA
        return mutantService.validate(specimen);

    }


}
