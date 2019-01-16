package com.joaera.diff.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class Difference {
    private LinkedList<Integer> offsets;
    private int length;
}
