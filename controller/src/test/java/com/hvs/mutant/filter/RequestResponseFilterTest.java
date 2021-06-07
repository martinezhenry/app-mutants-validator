package com.hvs.mutant.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SpringBootTest(classes = {RequestResponseFilter.class})
class RequestResponseFilterTest {

    @Autowired
    private RequestResponseFilter requestResponseFilter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    /**
     * Test to Filter
     */
    @Test
    void doFilterInternal() {

        Assertions.assertNotNull(requestResponseFilter);

        Assertions.assertDoesNotThrow(() -> requestResponseFilter.doFilterInternal(request, response, chain));


    }
}