package com.task.widget.util;

import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WidgetDtoConverterTest {

    @Test
    void convert() {
        Widget widget = Widget.builder().build();
        WidgetDtoConverter.convert(WidgetDto.builder().z(55).build(), widget);

        assertEquals(55, widget.getZzIndex());
    }
}
