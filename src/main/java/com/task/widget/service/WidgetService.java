package com.task.widget.service;

import com.task.widget.exception.NotFoundException;
import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;
import com.task.widget.model.WidgetSearchDto;
import com.task.widget.repository.WidgetRepository;
import com.task.widget.util.PageAssembler;
import com.task.widget.util.WidgetDtoConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@AllArgsConstructor
@Slf4j
public class WidgetService {
    private final WidgetRepository repository;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public Widget create(WidgetDto widgetDto) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Widget widget = new Widget(UUID.randomUUID());
            WidgetDtoConverter.convert(widgetDto, widget);
            return saveWidget(widget);
        } finally {
            writeLock.unlock();
        }
    }

    public Widget update(UUID id, WidgetDto widgetDto) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Widget widget = repository.findById(id).orElseThrow(() -> new NotFoundException("Widget Not Found", id));
            repository.delete(widget);
            WidgetDtoConverter.convert(widgetDto, widget);
            return saveWidget(widget);
        } finally {
            writeLock.unlock();
        }
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

    public Page<Widget> findAll(Pageable pageable, WidgetSearchDto searchDto) {
        Lock readLock = rwLock.readLock();
        readLock.lock();

        List<Widget> widgetList;
        try {
            widgetList = repository.findByOrderByZzIndexAsc();
        } finally {
            readLock.unlock();
        }

        List<Widget> filtered = WidgetFilter.filterWidgets(searchDto, widgetList);
        return PageAssembler.getPage(pageable, filtered);
    }

    public Widget findById(UUID id) {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            return repository.findById(id).orElseThrow(() -> new NotFoundException("Widget Not Found", id));
        } finally {
            readLock.unlock();
        }
    }

    public void delete(UUID id) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Widget byId = repository.findById(id).orElseThrow(() -> new NotFoundException("Widget Not Found", id));
            repository.delete(byId);
        } finally {
            writeLock.unlock();
        }
    }
}
