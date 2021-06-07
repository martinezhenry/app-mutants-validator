package com.hvs.mutant.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private String requestId;
    private Date datetime;
    private int status;
    private String message;
    private Specimen specimen;
    private StackTraceElement[] stackTraceElements;


}
