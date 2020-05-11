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
    private String filter;
    private Integer lowerX;
    private Integer lowerY;
    private Integer upperX;
    private Integer upperY;

    public boolean isNull() {
        return Stream.of(lowerX, lowerY, upperX, upperY).anyMatch(Objects::isNull);
    }
}
