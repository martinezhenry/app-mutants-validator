package com.hvs.mutant.shared.handler;

import com.hvs.mutant.model.Response;
import com.hvs.mutant.shared.config.AppConfig;
import com.hvs.mutant.shared.exception.DnaStructureException;
import com.hvs.mutant.shared.exception.NotMutantException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = {GeneralHandler.class, AppConfig.class})
class GeneralHandlerTest {

    @Autowired
    private GeneralHandler generalHandler;
    private String exceptionMessage;


    @BeforeEach
    void setUp() {

        Assertions.assertNotNull(generalHandler);

    }

    @Test
    void generalExceptionHandler() {
        ResponseEntity<Response> responseEntity = generalHandler
                .generalExceptionHandler(new Exception(exceptionMessage));

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Response response = responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        Assertions.assertEquals(exceptionMessage, response.getMessage());

    }

    @Test
    void notMutantExceptionHandler() {
        ResponseEntity<Response> responseEntity = generalHandler
                .notMutantExceptionHandler(new NotMutantException(exceptionMessage, null));

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Response response = responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        Assertions.assertEquals(exceptionMessage, response.getMessage());
    }

    @Test
    void dnaStructureExceptionHandler() {

        ResponseEntity<Response> responseEntity = generalHandler
                .dnaStructureExceptionHandler(new DnaStructureException(exceptionMessage));

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Response response = responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        Assertions.assertEquals(exceptionMessage, response.getMessage());
    }
}