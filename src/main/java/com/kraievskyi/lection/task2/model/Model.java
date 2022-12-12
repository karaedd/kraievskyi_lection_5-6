package com.kraievskyi.lection.task2.model;

import com.kraievskyi.lection.task2.annotaion.Property;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Model {
    private String stringProperty;
    @Property(name = "numberProperty")
    private int myNumber;
    @Property(format = "dd.MM.yyyy HH:mm")
    private Instant timeProperty;

    @Override
    public String toString() {
        return "Model{"
                + "stringProperty='"
                + stringProperty
                + '\''
                + ", myNumber="
                + myNumber
                + ", timeProperty="
                + timeProperty
                + '}';
    }
}
