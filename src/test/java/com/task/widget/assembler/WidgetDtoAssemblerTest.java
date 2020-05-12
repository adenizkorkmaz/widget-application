package com.task.widget.assembler;

import com.task.widget.model.Widget;
import com.task.widget.model.WidgetResponseDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WidgetDtoAssemblerTest {

    WidgetDtoAssembler dtoAssembler = new WidgetDtoAssembler();

    @Test
    void toModel_shouldConvertToPagedModel() {
        UUID id = UUID.randomUUID();
        Widget widget = Widget.builder()
                .id(id)
                .x(2)
                .y(4)
                .zzIndex(5)
                .height(55)
                .width(44)
                .build();

        WidgetResponseDto responseDto = dtoAssembler.toModel(widget);

        assertEquals(5, responseDto.getZ());
        assertEquals(2, responseDto.getX());
        assertEquals(4, responseDto.getY());
        assertEquals(55, responseDto.getHeight());
        assertEquals(44, responseDto.getWidth());
        assertEquals(id, responseDto.getId());
        assertEquals("</widgets/" + id + ">;rel=\"self\"",
                responseDto.getLink("self").get().toString());


    }
}
