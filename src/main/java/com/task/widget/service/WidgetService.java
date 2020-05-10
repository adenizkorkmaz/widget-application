package com.task.widget.service;

import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;
import com.task.widget.model.WidgetSearchDto;
import com.task.widget.repository.WidgetRepository;
import com.task.widget.util.PageAssembler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class WidgetService {
    private final WidgetRepository repository;

    public Widget create(WidgetDto widgetDto) {
        Widget widget = new Widget();
        widget.setId(UUID.randomUUID());
        widget.setLastModificationDate(LocalDateTime.now());
        widget.setZzIndex(widgetDto.getZ());
        BeanUtils.copyProperties(widgetDto, widget);
        return saveWidget(widget);
    }

    public Widget update(UUID id, WidgetDto widgetDto) {
        Widget widget = findById(id);
        repository.delete(widget);
        widget.setLastModificationDate(LocalDateTime.now());
        widget.setZzIndex(widgetDto.getZ());
        BeanUtils.copyProperties(widgetDto, widget);
        return saveWidget(widget);
    }

    private Widget saveWidget(Widget widget) {
        if (widget.getZzIndex() == null) {
            Widget foundWidget = repository.findFirstByOrderByZzIndexDesc();
            int z = 0;
            if (foundWidget != null) {
                z = foundWidget.getZzIndex() + 1;
            }
            widget.setZzIndex(z);
            return repository.save(widget);

        } else {
            Widget foundWidget = repository.findByZzIndex(widget.getZzIndex());
            if (foundWidget != null) {
                shiftWidgets(widget);
            }
            return repository.save(widget);
        }
    }

    private void shiftWidgets(Widget widget) {
        List<Widget> listOfGreaterZIndex = repository.findByZzIndexGreaterThanEqual(widget.getZzIndex());
        listOfGreaterZIndex.forEach(w -> {
            w.setZzIndex(w.getZzIndex() + 1);
            repository.save(w);
        });
    }

    public Page<Widget> findAll(Pageable pageable, @Valid WidgetSearchDto searchDto) {
        List<Widget> byOrderByZzIndexAsc = repository.findByOrderByZzIndexAsc();
        return PageAssembler.getPage(pageable, byOrderByZzIndexAsc);
    }

    public Widget findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public void delete(UUID id) {
        Widget byId = findById(id);
        repository.delete(byId);
    }
}
