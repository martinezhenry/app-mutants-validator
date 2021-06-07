package com.hvs.mutant.shared.exception;

import com.hvs.mutant.model.Specimen;
import lombok.Getter;
import lombok.Setter;


public class NotMutantException extends RuntimeException {

    public NotMutantException(String message, Specimen specimen) {
        super(message);
        this.specimen = specimen;
    }

    @Getter
    private Specimen specimen;

}
