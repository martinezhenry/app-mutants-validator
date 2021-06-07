package com.hvs.mutant.shared.util;

import com.hvs.mutant.model.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

class MutantUtilTest {

    private String message;
    private HttpStatus status;
    private char[][] table;
    private StackTraceElement[] stackTraceElements;
    private double amount;
    private double amountExpected;
    private String pattern;
    private Logger logger = LoggerFactory.getLogger(MutantUtilTest.class);

    @BeforeEach
    void setUp() {
        message = "This a test";
        status = HttpStatus.OK;
        table = new char[][]{
                {'T', 'A', 'G', 'T'}
                , {'T', 'A', 'G', 'T'}
                , {'T', 'A', 'G', 'T'}
                , {'T', 'A', 'G', 'T'}
        };
        Exception exception = new Exception();
        stackTraceElements = exception.getStackTrace();
        amount = 100.12345;
        amountExpected = 100.12;
        pattern = "#.##";

    }

    @Test
    void printDnaTable() {

        Assertions.assertDoesNotThrow(() -> MutantUtil.printDnaTable(table, logger));
        Assertions.assertThrows(NullPointerException.class
                , () -> MutantUtil.printDnaTable(null, logger));


    }

    @Test
    void buildResponse() {
        Response response = MutantUtil.buildResponse(message, status.value(), null, null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(message, response.getMessage());
        Assertions.assertEquals(status.value(), response.getStatus());
        Assertions.assertNull(response.getSpecimen());
    }

    @Test
    void testBuildResponse() {
        Response response = MutantUtil.buildResponse(message, status.value(), null, stackTraceElements, true);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(message, response.getMessage());
        Assertions.assertEquals(status.value(), response.getStatus());
        Assertions.assertEquals(stackTraceElements, response.getStackTraceElements());
        Assertions.assertNull(response.getSpecimen());
    }

    @Test
    void formatDouble() {
        double formatted = MutantUtil.formatDouble(amount, pattern);
        Assertions.assertEquals(amountExpected, formatted);


    }
}