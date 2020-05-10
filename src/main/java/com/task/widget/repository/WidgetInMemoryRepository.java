package com.task.widget.repository;

import com.task.widget.model.Widget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@Repository
@ConditionalOnProperty(name = "storage.type", havingValue = "memory")
@Slf4j
public class WidgetInMemoryRepository implements WidgetRepository {

    private ConcurrentSkipListSet<Widget> widgets = new ConcurrentSkipListSet<>(Comparator.comparingInt(Widget::getZzIndex));

    @Override
    public Widget findFirstByOrderByZzIndexDesc() {
        if (widgets.isEmpty()) {
            return null;
        }
        return widgets.last();
    }

    @Override
    public Widget findByZzIndex(Integer zIndex) {
        return widgets.stream().filter(widget -> widget.getZzIndex().equals(zIndex)).findFirst().orElse(null);
    }

    @Override
    public List<Widget> findByZzIndexGreaterThanEqual(Integer zIndex) {
        return widgets.stream().filter(widget -> widget.getZzIndex().compareTo(zIndex) >= 0).collect(Collectors.toList());
    }

    @Override
    public List<Widget> findByOrderByZzIndexAsc() {
        return new ArrayList<>(this.widgets);
    }

    @Override
    public Widget save(Widget widget) {
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
