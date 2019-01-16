package com.joaera.diff.models;

import lombok.Data;

//Model for output
@Data
public class DiffResult {
    private String message;
    private Difference difference;
}
