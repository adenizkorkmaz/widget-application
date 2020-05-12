package com.task.widget.service;

import com.task.widget.exception.BadRequestException;
import com.task.widget.model.Widget;
import com.task.widget.model.WidgetSearchDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

public class WidgetFilter {
    public static List<Widget> filterWidgets(@Valid WidgetSearchDto searchDto, List<Widget> widgetList) {
        if (searchDto.isNull()) {
            return widgetList;
        }

        String[] lower = searchDto.getLowerLeft().split(":");
        String[] upper = searchDto.getUpperRight().split(":");

        if (lower.length != 2 || upper.length != 2) {
            throw new BadRequestException("Wrong filter format. Example : /widgets?lowerLeft=0:0&upperRight=100:150");
        }

        int lowerX, lowerY, upperX, upperY;
        try {
            lowerX = Integer.parseInt(lower[0]);
            lowerY = Integer.parseInt(lower[1]);
            upperX = Integer.parseInt(upper[0]);
            upperY = Integer.parseInt(upper[1]);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Please enter valid integers for filter.");
        }

        return widgetList.stream()
                .filter(widget -> {
                            Widget.Coordinate lowerLeftCorner = widget.getLowerLeftCorner();
                            Widget.Coordinate upperRightCorner = widget.getUpperRightCorner();

                            return lowerLeftCorner.getX() >= lowerX && lowerLeftCorner.getX() <= upperX &&
                                    lowerLeftCorner.getY() >= lowerY && lowerLeftCorner.getY() <= upperY &&
                                    upperRightCorner.getX() >= lowerX && upperRightCorner.getX() <= upperX &&
                                    upperRightCorner.getY() >= lowerY && upperRightCorner.getY() <= upperY;
                        }
                )
                .collect(Collectors.toList());
    }
}
