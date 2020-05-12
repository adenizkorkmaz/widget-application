package com.task.widget.util;

import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;

import java.time.LocalDateTime;

public class WidgetDtoConverter {
    public static void convert(WidgetDto dto, Widget widget) {
        widget.setLastModificationDate(LocalDateTime.now());
        widget.setZzIndex(dto.getZ());
        widget.setX(dto.getX());
        widget.setY(dto.getY());
        widget.setHeight(dto.getHeight());
        widget.setWidth(dto.getWidth());
    }
}
