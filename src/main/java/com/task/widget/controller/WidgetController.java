package com.task.widget.controller;

import com.task.widget.assembler.AquariumDtoAssembler;
import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;
import com.task.widget.model.WidgetResponseDto;
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
    private final PagedResourcesAssembler<Widget> pagedResourcesAssembler;
    private final AquariumDtoAssembler aquariumDtoAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    //TODO: default 10 max 500 page size
    public PagedModel<WidgetResponseDto> findAll(Pageable pageable) {
        Page<Widget> all = widgetService.findAll(pageable);
        return pagedResourcesAssembler.toModel(all, aquariumDtoAssembler);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Widget findById(@PathVariable("id") UUID id) {
        return widgetService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Widget create(@Valid @RequestBody WidgetDto widgetDto) {
        return widgetService.create(widgetDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Widget update(@PathVariable("id") UUID id, @Valid @RequestBody WidgetDto widgetDto) {
        return widgetService.update(id, widgetDto);
    }
}
