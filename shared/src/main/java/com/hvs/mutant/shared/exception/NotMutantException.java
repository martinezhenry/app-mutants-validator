package com.hvs.mutant.shared.exception;

import com.hvs.mutant.model.Specimen;
import lombok.Getter;

/**
 * Exception Class to thrown when DNA is not mutant
 */
public class NotMutantException extends RuntimeException {

    public NotMutantException(String message, Specimen specimen) {
        super(message);
        this.specimen = specimen;
    }

    @Getter
    private final Specimen specimen;

}
