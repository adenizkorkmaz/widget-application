package com.task.widget.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WidgetSearchDto {
    /**
     * /widgets?lowerLeft=0:0&upperRight=100:150
     */
    private String lowerLeft;
    private String upperRight;

    public boolean isNull() {
        return Stream.of(lowerLeft, upperRight).anyMatch(Objects::isNull);
    }
}
