package com.hvs.mutant.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationData {

    private List<Sequence> sequences;
    //private int sequences;

    public ValidationData() {
        sequences = new ArrayList<>();
    }

}
