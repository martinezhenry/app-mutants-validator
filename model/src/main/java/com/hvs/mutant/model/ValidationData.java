package com.hvs.mutant.model;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Model to save sequences detected
 */
public class ValidationData {

    @Getter
    private List<Sequence> sequences;
    public ValidationData() {
        sequences = new ArrayList<>();
    }

}
