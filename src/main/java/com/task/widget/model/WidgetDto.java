package com.task.widget.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WidgetDto {
    @NotNull
    private Integer x;

    @NotNull
    private Integer y;

    private Integer z;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;
}
