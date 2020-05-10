package com.task.widget.service;

import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;
import com.task.widget.repository.WidgetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        BeanUtils.copyProperties(widgetDto, widget);

        return repository.save(widget);
    }

    public Widget update(UUID id, WidgetDto widgetDto) {
        Widget widget = repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        repository.delete(widget);
        widget.setLastModificationDate(LocalDateTime.now());
        BeanUtils.copyProperties(widgetDto, widget);
        return repository.save(widget);
    }

    public Page<Widget> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Widget findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    }

}
