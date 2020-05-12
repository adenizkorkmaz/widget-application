package com.task.widget.controller;

import com.task.widget.assembler.WidgetDtoAssembler;
import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;
import com.task.widget.model.WidgetResponseDto;
import com.task.widget.model.WidgetSearchDto;
import com.task.widget.service.WidgetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(
            value = "View all widgets",
            notes = "To filter widgets according to a given area you should call this method with request param. " +
                    "Example : /widgets?lowerLeft=0:0&upperRight=100:150",
            response = WidgetResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Widgets successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request."),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<WidgetResponseDto> findAll(@Valid WidgetSearchDto searchDto,
                                                 Pageable pageable, PagedResourcesAssembler<Widget> pagedResourcesAssembler) {
        Page<Widget> all = widgetService.findAll(pageable, searchDto);
        return pagedResourcesAssembler.toModel(all, widgetDtoAssembler);
    }


    @ApiOperation(value = "View a widget with given id", response = WidgetResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Widget successfully retrieved"),
            @ApiResponse(code = 404, message = "The widget you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WidgetResponseDto findById(@PathVariable("id") UUID id) {
        Widget widget = widgetService.findById(id);
        return widgetDtoAssembler.toModel(widget);
    }


    @ApiOperation(value = "Create new widget", response = WidgetResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Widget successfully created"),
            @ApiResponse(code = 400, message = "Bad request"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WidgetResponseDto create(@Valid @RequestBody WidgetDto widgetDto) {
        Widget widget = widgetService.create(widgetDto);
        return widgetDtoAssembler.toModel(widget);
    }


    @ApiOperation(value = "Update given widget", response = WidgetResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Widget successfully updated"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "The widget you were trying to update is not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WidgetResponseDto update(@PathVariable("id") UUID id, @Valid @RequestBody WidgetDto widgetDto) {
        Widget widget = widgetService.update(id, widgetDto);
        return widgetDtoAssembler.toModel(widget);
    }


    @ApiOperation(value = "Delete given widget", response = WidgetResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Widget successfully deleted"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "The widget you were trying to delete is not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        widgetService.delete(id);
    }
}
