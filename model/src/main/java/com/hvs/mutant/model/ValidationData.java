package com.hvs.mutant.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class ValidationData {

    /*private char previosH;
    private char previosV;
    private char previosO;
    private char previosI;
    private String sequenceH;
    private String sequenceV;
    private String sequenceO;
    private String sequenceI;*/
    private List<Sequence> sequences;
    //private int sequences;

}
