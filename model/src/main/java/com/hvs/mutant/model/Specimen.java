package com.hvs.mutant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Specimen {
    @JsonIgnore
    @Id
    private long id;
    private String[] dna;
    private List<Sequence> sequences;
    private boolean mutant;
}
