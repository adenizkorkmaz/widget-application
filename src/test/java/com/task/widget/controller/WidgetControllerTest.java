package com.task.widget.controller;

import com.task.widget.assembler.WidgetDtoAssembler;
import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;
import com.task.widget.model.WidgetResponseDto;
import com.task.widget.model.WidgetSearchDto;
import com.task.widget.service.WidgetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class WidgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WidgetDtoAssembler widgetDtoAssembler;

    @MockBean
    private WidgetService widgetService;

    @MockBean
    private PagedResourcesAssembler<Widget> pagedResourcesAssembler;

    @Test
    void findAll() throws Exception {
        Widget widget = Widget.builder().build();
        UUID id = UUID.randomUUID();
        WidgetResponseDto responseDto = WidgetResponseDto.builder().x(3).y(4).z(5)
                .width(10).height(10).id(id).build();

        UUID id2 = UUID.randomUUID();
        WidgetResponseDto responseDto2 = WidgetResponseDto.builder().x(30).y(40).z(50)
                .width(10).height(10).id(id2).build();

        PageImpl<Widget> page = new PageImpl<>(Collections.singletonList(widget));
        when(widgetService.findAll(any(Pageable.class), any(WidgetSearchDto.class))).thenReturn(page);

        PagedModel<WidgetResponseDto> pagedModel = new PagedModel<>(Arrays.asList(responseDto, responseDto2),
                new PagedModel.PageMetadata(2, 0, 2, 1));
        when(pagedResourcesAssembler.toModel(page, widgetDtoAssembler))
                .thenReturn(pagedModel);

        mockMvc.perform(get("/widgets")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(id.toString()))
                .andExpect(jsonPath("$.content[0].x").value("3"))
                .andExpect(jsonPath("$.content[0].y").value("4"))
                .andExpect(jsonPath("$.content[0].z").value("5"))
                .andExpect(jsonPath("$.content[0].width").value("10"))
                .andExpect(jsonPath("$.content[0].height").value("10"))
                .andExpect(jsonPath("$.page.size").value("2"))
                .andExpect(jsonPath("$.page.totalElements").value("2"))
                .andExpect(jsonPath("$.page.totalPages").value("1"));
    }

    @Test
    void findById() throws Exception {
        Widget widget = Widget.builder().build();
        UUID id = UUID.randomUUID();
        WidgetResponseDto responseDto = WidgetResponseDto.builder().x(3).y(4).z(5)
                .width(10).height(10).id(id).build();

        when(widgetService.findById(id)).thenReturn(widget);
        when(widgetDtoAssembler.toModel(widget)).thenReturn(responseDto);

        mockMvc.perform(get("/widgets/{id}", id.toString())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.x").value("3"))
                .andExpect(jsonPath("$.y").value("4"))
                .andExpect(jsonPath("$.z").value("5"))
                .andExpect(jsonPath("$.width").value("10"))
                .andExpect(jsonPath("$.height").value("10"));
    }

    @Test
    void create() throws Exception {

        Widget widget = Widget.builder().build();
        UUID id = UUID.randomUUID();
        WidgetResponseDto responseDto = WidgetResponseDto.builder().x(3).y(4).z(5)
                .width(10).height(10).id(id).build();

        when(widgetService.create(any(WidgetDto.class))).thenReturn(widget);
        when(widgetDtoAssembler.toModel(widget)).thenReturn(responseDto);

        mockMvc.perform(post("/widgets")
                .contentType("application/json").content("{\n" +
                        "  \"x\":3,\n" +
                        "  \"y\": 4,\n" +
                        "  \"z\" : 5,\n" +
                        "  \"width\" : 10,\n" +
                        "  \"height\" : 10 \n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.x").value("3"))
                .andExpect(jsonPath("$.y").value("4"))
                .andExpect(jsonPath("$.z").value("5"))
                .andExpect(jsonPath("$.width").value("10"))
                .andExpect(jsonPath("$.height").value("10"));
    }


    @Test
    void update() throws Exception {

        Widget widget = Widget.builder().build();
        UUID id = UUID.randomUUID();
        WidgetResponseDto responseDto = WidgetResponseDto.builder().x(3).y(4).z(5)
                .width(10).height(10).id(id).build();

        when(widgetService.update(eq(id), any(WidgetDto.class))).thenReturn(widget);
        when(widgetDtoAssembler.toModel(widget)).thenReturn(responseDto);

        mockMvc.perform(put("/widgets/{id}", id.toString())
                .contentType("application/json").content("{\n" +
                        "  \"x\":3,\n" +
                        "  \"y\": 4,\n" +
                        "  \"z\" : 5,\n" +
                        "  \"width\" : 10,\n" +
                        "  \"height\" : 10 \n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.x").value("3"))
                .andExpect(jsonPath("$.y").value("4"))
                .andExpect(jsonPath("$.z").value("5"))
                .andExpect(jsonPath("$.width").value("10"))
                .andExpect(jsonPath("$.height").value("10"));
    }
}
