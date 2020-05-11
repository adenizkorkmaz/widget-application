package com.task.widget.service;

import com.task.widget.model.Widget;
import com.task.widget.model.WidgetSearchDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

public class WidgetFilter {
    public static List<Widget> filterWidgets(@Valid WidgetSearchDto searchDto, List<Widget> byOrderByZzIndexAsc) {
        if (searchDto.isNull()) {
            return byOrderByZzIndexAsc;
        }

        return byOrderByZzIndexAsc.stream().filter(widget -> {
            Widget.Coordinate lowerLeftCorner = widget.getLowerLeftCorner();
            Widget.Coordinate upperRightCorner = widget.getUpperRightCorner();
            return lowerLeftCorner.getX() >= searchDto.getLowerX() && lowerLeftCorner.getX() <= searchDto.getUpperX() &&
                    lowerLeftCorner.getY() >= searchDto.getLowerY() && lowerLeftCorner.getY() <= searchDto.getUpperY() &&
                    upperRightCorner.getX() >= searchDto.getLowerX() && upperRightCorner.getX() <= searchDto.getUpperX() &&
                    upperRightCorner.getY() >= searchDto.getLowerY() && upperRightCorner.getY() <= searchDto.getUpperY();
        }).collect(Collectors.toList());
    }
}
