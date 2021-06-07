package com.hvs.mutant.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {RequestResponseFilter.class})
class RequestResponseFilterTest {

    @Autowired
    private RequestResponseFilter requestResponseFilter;

    @Test
    void doFilterInternal() {

        Assertions.assertNotNull(requestResponseFilter);

    }
}