package com.task.widget.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WidgetTest {

    @Test
    void getLowerLeftCorner() {
        Widget widget = Widget.builder()
                .x(10)
                .y(10)
                .height(100)
                .width(100)
                .build();

        assertEquals(-40, widget.getLowerLeftCorner().getY());
        assertEquals(-40, widget.getLowerLeftCorner().getX());
    }

    @Test
    void getUpperRightCorner() {

        Widget widget = Widget.builder()
                .x(10)
                .y(10)
                .height(100)
                .width(100)
                .build();

        assertEquals(60, widget.getUpperRightCorner().getY());
        assertEquals(60, widget.getUpperRightCorner().getX());
    }
}
