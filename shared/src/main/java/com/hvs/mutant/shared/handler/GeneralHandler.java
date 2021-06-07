package com.hvs.mutant.shared.handler;

import com.hvs.mutant.model.Response;
import com.hvs.mutant.shared.config.AppConfig;
import com.hvs.mutant.shared.exception.DnaStructureException;
import com.hvs.mutant.shared.exception.NotMutantException;
import com.hvs.mutant.shared.util.MutantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralHandler {

    private Logger logger = LoggerFactory.getLogger(GeneralHandler.class);
    private AppConfig config;

    public GeneralHandler(AppConfig config) {
        this.config = config;

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> generalExceptionHandler(Exception e) {
        logger.error("GeneralException: {}", e.getMessage(), e);
        var message = MutantUtil.buildResponse(e.getMessage()
                , HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getStackTrace(), config.isDebug());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);

    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotMutantException.class)
    public ResponseEntity<Response> notMutantExceptionHandler(NotMutantException e) {
        logger.debug("NotMutantException: {}", e.getMessage());
        var message = MutantUtil.buildResponse(e.getMessage()
                , HttpStatus.FORBIDDEN.value(), e.getSpecimen(), e.getStackTrace(), config.isDebug());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DnaStructureException.class)
    public ResponseEntity<Response> dnaStructureExceptionHandler(DnaStructureException e) {
        logger.debug("DNAStructureException: {}", e.getMessage(), e);
        var message = MutantUtil.buildResponse(e.getMessage()
                , HttpStatus.BAD_REQUEST.value(), null, e.getStackTrace(), config.isDebug());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

    }


}
