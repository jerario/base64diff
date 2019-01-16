package com.joaera.diff.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Model use for receiving data from the clients.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String data;
}
