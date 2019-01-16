package com.joaera.diff.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

//Model for differences between sides.
@Data
@AllArgsConstructor
public class Difference {
    private LinkedList<Integer> offsets;
    private int length;
}
