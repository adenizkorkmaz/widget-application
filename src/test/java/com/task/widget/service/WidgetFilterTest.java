package com.task.widget.service;

import com.task.widget.exception.BadRequestException;
import com.task.widget.model.Widget;
import com.task.widget.model.WidgetSearchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

class WidgetFilterTest {

    @Test
    void filterWidgets_shouldReturnSameList_whenSearchFieldsAreNull() {
        List<Widget> widgets = Arrays.asList(Widget.builder().build());

        List<Widget> widgetList = WidgetFilter.filterWidgets(
                WidgetSearchDto.builder().build(),
                widgets);

        Assertions.assertEquals(widgetList, widgets);
    }

    @Test
    void filterWidgets_shouldBadRequest_whenFieldsAreNotValid() {
        List<Widget> widgets = Arrays.asList(Widget.builder().build());

        WidgetSearchDto searchDto = WidgetSearchDto.builder().lowerLeft("3").upperRight("2").build();

        assertThrows(BadRequestException.class, () -> WidgetFilter.filterWidgets(searchDto, widgets));

    }

    @Test
    void filterWidgets_shouldBadRequest_whenFieldsAreNotValidIntegers() {
        List<Widget> widgets = Arrays.asList(Widget.builder().build());

        WidgetSearchDto searchDto = WidgetSearchDto.builder().lowerLeft("3:f").upperRight("2:f").build();

        assertThrows(BadRequestException.class, () -> WidgetFilter.filterWidgets(searchDto, widgets));

    }

    @Test
    void filterWidgets_shouldFilterSuccessfully() {
        Widget widget = Widget.builder().x(50).y(50).height(100).width(100).build();
        Widget widget2 = Widget.builder().x(50).y(100).height(100).width(100).build();
        Widget widget3 = Widget.builder().x(100).y(100).height(100).width(100).build();

        List<Widget> widgets = Arrays.asList(widget, widget2, widget3);

        WidgetSearchDto searchDto = WidgetSearchDto.builder().lowerLeft("0:0").upperRight("100:150").build();

        List<Widget> widgetList = WidgetFilter.filterWidgets(searchDto, widgets);

        assertEquals(2, widgetList.size());

        assertFalse(widgetList.contains(widget3));
        assertThat(widgetList, hasItems(widget, widget2));
    }
}
