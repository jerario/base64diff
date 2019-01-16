package com.joaera.diff.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Arrays;
import java.util.LinkedList;

@Data
@Entity
@AllArgsConstructor
public class Document {
    @Id
    private String id;
    @Lob
    @Column(length = 64000)
    private String left;
    @Lob
    @Column(length = 64000)
    private String right;

    public Document() {

    }

    public Document(String id) {
        this.id = id;
    }

    public boolean equalsSides() {
        byte[] leftBytes = left.getBytes();
        byte[] rightBytes = right.getBytes();

        return Arrays.equals(leftBytes, rightBytes);
    }

    public boolean equalsLength() {
        byte[] leftBytes = left.getBytes();
        byte[] rightBytes = right.getBytes();

        return leftBytes.length == rightBytes.length;
    }

    public LinkedList<Integer> diffSides() {
        LinkedList<Integer> offsets = new LinkedList<>();
        byte[] leftBytes = left.getBytes();
        byte[] rightBytes = right.getBytes();
        if (leftBytes.length == rightBytes.length) {
            byte different = 0;
            for (int i = 0; i < leftBytes.length; i++) {
                different = (byte) (leftBytes[i] ^ rightBytes[i]);
                if (different != 0) {
                    offsets.add(i);
                }
            }
        }
        return offsets;
    }
}
