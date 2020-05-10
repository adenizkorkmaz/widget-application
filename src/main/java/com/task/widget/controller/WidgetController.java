package com.task.widget.controller;

import com.task.widget.assembler.WidgetDtoAssembler;
import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;
import com.task.widget.model.WidgetResponseDto;
import com.task.widget.model.WidgetSearchDto;
import com.task.widget.service.WidgetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/widgets")
@AllArgsConstructor
@Slf4j
public class WidgetController {
    private final WidgetService widgetService;
    private final WidgetDtoAssembler widgetDtoAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<WidgetResponseDto> findAll(@Valid WidgetSearchDto searchDto,
                                                 Pageable pageable, PagedResourcesAssembler<Widget> pagedResourcesAssembler) {
        Page<Widget> all = widgetService.findAll(pageable, searchDto);
        return pagedResourcesAssembler.toModel(all, widgetDtoAssembler);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WidgetResponseDto findById(@PathVariable("id") UUID id) {
        Widget widget = widgetService.findById(id);
        return widgetDtoAssembler.toModel(widget);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WidgetResponseDto create(@Valid @RequestBody WidgetDto widgetDto) {
        Widget widget = widgetService.create(widgetDto);
        return widgetDtoAssembler.toModel(widget);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WidgetResponseDto update(@PathVariable("id") UUID id, @Valid @RequestBody WidgetDto widgetDto) {
        Widget widget = widgetService.update(id, widgetDto);
        return widgetDtoAssembler.toModel(widget);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        widgetService.delete(id);
    }
}
