package com.task.widget.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WidgetSearchDto {
    private Integer lowerX;
    private Integer lowerY;
    private Integer upperX;
    private Integer upperY;
}
