package com.joaera.diff.models;

import lombok.Data;

@Data
public class DiffResult {
    private String message;
    private Difference difference;
}
