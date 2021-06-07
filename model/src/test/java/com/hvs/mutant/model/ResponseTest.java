package com.hvs.mutant.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

class ResponseTest {

    private Response response;
    private String testId;
    private String message;
    private Specimen specimen;

    @BeforeEach
    void setUp() {
        response = new Response();
        Assertions.assertNotNull(response);
        testId = "TestId";
        message = "Test Message";
        specimen = new Specimen();
    }

    @Test
    void requestId() {
        Assertions.assertNull(response.getRequestId());
        response.setRequestId(testId);
        Assertions.assertEquals(testId, response.getRequestId());

    }

    @Test
    void datetime() {
        Date date = new Date();
        Assertions.assertNull(response.getDatetime());
        response.setDatetime(date);
        Assertions.assertEquals(date, response.getDatetime());
    }

    @Test
    void status() {
        Assertions.assertEquals(0, response.getStatus());
        response.setStatus(1);
        Assertions.assertEquals(1, response.getStatus());
    }

    @Test
    void message() {
        Assertions.assertNull(response.getMessage());
        response.setMessage(message);
        Assertions.assertEquals(message, response.getMessage());
    }

    @Test
    void specimen() {
        Assertions.assertNull(response.getSpecimen());
        response.setSpecimen(specimen);
        Assertions.assertEquals(specimen, response.getSpecimen());
    }

    @Test
    void stackTraceElements() {
        Exception exception = new Exception();
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        Assertions.assertNull(response.getStackTraceElements());
        response.setStackTraceElements(stackTraceElements);
        Assertions.assertEquals(stackTraceElements, response.getStackTraceElements());
    }
}