package com.hvs.mutant.shared.handler;

import com.hvs.mutant.shared.exception.NotMutantException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralHandler {

        @ResponseStatus(HttpStatus.FORBIDDEN)
        @ExceptionHandler(NotMutantException.class)
        public void notMutantExceptionHandler() {

        }


}
