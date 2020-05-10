package com.task.widget.assembler;

import com.task.widget.controller.WidgetController;
import com.task.widget.model.Widget;
import com.task.widget.model.WidgetResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AquariumDtoAssembler extends RepresentationModelAssemblerSupport<Widget, WidgetResponseDto> {

    public AquariumDtoAssembler() {
        super(WidgetController.class, WidgetResponseDto.class);
    }

    @Override
    public WidgetResponseDto toModel(Widget entity) {
        WidgetResponseDto responseDto = new WidgetResponseDto();
        BeanUtils.copyProperties(entity, responseDto);
        responseDto.add(linkTo(methodOn(WidgetController.class).findById(responseDto.getId())).withSelfRel());
        return responseDto;
    }

//    @Override
//    public CollectionModel<WidgetResponseDto> toCollectionModel(Iterable<? extends Widget> entities) {
//        CollectionModel<WidgetResponseDto> widgetResponseDtos = super.toCollectionModel(entities);
//        widgetResponseDtos.add(linkTo(methodOn(WidgetController.class).findAll()).withSelfRel());
//        return widgetResponseDtos;
//    }

}
