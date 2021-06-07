package com.hvs.mutant.controller.impl;

import com.hvs.mutant.controller.MutantController;
import com.hvs.mutant.model.Response;
import com.hvs.mutant.model.Specimen;
import com.hvs.mutant.service.MutantService;
import com.hvs.mutant.shared.exception.NotMutantException;
import com.hvs.mutant.shared.util.MutantUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

@SpringBootTest(classes = {MutantControllerImpl.class})
class MutantControllerImplTest {

    @Autowired
    private MutantController mutantController;
    @MockBean
    private MutantService mutantService;


    private Specimen specimen;

    @BeforeEach
    void setUp() {
        specimen = new Specimen();

    }

    @Test
    void notMutant() {
        specimen.setDna(new String[]{""});
        Mockito.when(mutantService.validate(Mockito.any())).thenReturn(new Response());
        Assertions.assertNotNull(mutantController);
        Assertions.assertThrows(NotMutantException.class, () -> mutantController.mutant(specimen));


    }

    @Test
    void Mutant() {
        specimen.setDna(new String[]{"111156", "113456", "431556", "675156", "153467", "123478"});
        specimen.setMutant(true);
        HttpStatus status = HttpStatus.OK;
        Response response = MutantUtil.buildResponse(status.name(), status.value(), specimen, null);
        Mockito.when(mutantService.validate(Mockito.any())).thenReturn(response);
        Assertions.assertNotNull(mutantController);
        Assertions.assertDoesNotThrow(() -> mutantController.mutant(specimen));

    }
}