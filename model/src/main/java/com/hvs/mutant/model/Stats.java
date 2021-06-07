package com.hvs.mutant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Data
public class Stats {

    @JsonIgnore
    @Id
    private String id;
    @JsonProperty("count_mutant_dna")
    private int countMutantDna;
    @JsonProperty("count_human_dna")
    private int countHumanDna;
    private double ratio;
}
