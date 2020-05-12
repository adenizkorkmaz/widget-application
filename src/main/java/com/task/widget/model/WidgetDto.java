package com.task.widget.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class WidgetDto {
    @NotNull
    private Integer x;

    @NotNull
    private Integer y;

    private Integer z;

    @NotNull
    @Min(1)
    private Integer width;

    @NotNull
    @Min(1)
    private Integer height;
}
