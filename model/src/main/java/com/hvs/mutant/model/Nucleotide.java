package com.hvs.mutant.model;

import lombok.Data;

/**
 * Model to represent a DNA's component
 */
@Data
public class Nucleotide {
    private char data;
    private int xCoordinate;
    private int yCoordinate;
}
