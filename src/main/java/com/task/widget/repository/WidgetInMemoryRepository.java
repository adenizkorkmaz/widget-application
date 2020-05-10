package com.task.widget.repository;

import com.task.widget.model.Widget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

@Repository
@ConditionalOnProperty(name = "storage.type", havingValue = "memory")
@Slf4j
public class WidgetInMemoryRepository implements WidgetRepository {

    private ConcurrentSkipListSet<Widget> widgets = new ConcurrentSkipListSet<>(Comparator.comparingInt(Widget::getZ));

    @Override
    public Page<Widget> findAll(Pageable pageable) {
        List<Widget> widgetList = new Vector<>(this.widgets);
        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > widgets.size() ? widgets.size() : (start + pageable.getPageSize());
        return new PageImpl<>(widgetList.subList(Math.toIntExact(start), Math.toIntExact(end)), pageable, widgetList.size());
    }

    @Override
    public Widget save(Widget widget) {
        log.info(widget.toString());
        if (widget.getZ() == null) {
            Widget last = widgets.last();
            widget.setZ(last.getZ() + 1);
        } else {
            if (widgets.add(widget)) {
                return widget;
            }
            NavigableSet<Widget> newSet = widgets.tailSet(widget, true);
            newSet.forEach(w -> w.setZ(w.getZ() + 1));
        }
        widgets.add(widget);
        return widget;
    }

    @Override
    public Optional<Widget> findById(UUID uuid) {
        return widgets.stream().filter(widget1 -> widget1.getId().equals(uuid)).findFirst();
    }

    public void deleteById(UUID uuid) {
        Widget widget = findById(uuid).orElseThrow(() -> new RuntimeException("Not Found"));
        widgets.remove(widget);
    }

    @Override
    public void delete(Widget widget) {
        widgets.remove(widget);
    }
}
