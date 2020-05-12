package com.task.widget.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WidgetSearchDtoTest {

    @Test
    void isNull() {
        WidgetSearchDto build = WidgetSearchDto.builder().build();
        assertTrue(build.isNull());
    }

    @Test
    void isNull_whenOneOfTheFieldsIsNullAndTheOtherIsNot() {
        WidgetSearchDto build = WidgetSearchDto.builder().lowerLeft("2:3").build();
        assertTrue(build.isNull());
    }

    @Test
    void isNotNull() {
        WidgetSearchDto build = WidgetSearchDto.builder()
                .lowerLeft("4:4")
                .upperRight("3:3").build();
        assertFalse(build.isNull());
    }

}
