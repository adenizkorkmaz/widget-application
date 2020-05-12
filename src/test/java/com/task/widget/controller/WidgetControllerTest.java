package com.task.widget.controller;

import com.task.widget.assembler.WidgetDtoAssembler;
import com.task.widget.model.Widget;
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

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        WidgetResponseDto responseDto = WidgetResponseDto.builder().id(id).build();
        PageImpl<Widget> page = new PageImpl<>(Collections.singletonList(widget));
        when(widgetService.findAll(any(Pageable.class), any(WidgetSearchDto.class))).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, widgetDtoAssembler))
                .thenReturn(new PagedModel<>(Collections.singletonList(responseDto), null));

        mockMvc.perform(get("/widgets")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }


    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
