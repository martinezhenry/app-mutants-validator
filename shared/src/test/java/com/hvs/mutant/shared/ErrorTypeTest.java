package com.hvs.mutant.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ErrorTypeTest {

    @Test
    void values() {
        Assertions.assertNotNull(ErrorType.values());
        Assertions.assertTrue((ErrorType.values().length > 0));

    }

    @Test
    void valueOf() {
        String ERROR_TYE_MSG = ErrorType.SEQ_LENGTH_INVALID.name();
        Assertions.assertEquals(ErrorType.SEQ_LENGTH_INVALID, ErrorType.valueOf(ERROR_TYE_MSG));

    }
}