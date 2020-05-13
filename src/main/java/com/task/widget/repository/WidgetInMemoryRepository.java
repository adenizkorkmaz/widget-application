package com.task.widget.repository;

import com.task.widget.model.Widget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@ConditionalOnProperty(name = "storage.type", havingValue = "memory")
@Slf4j
public class WidgetInMemoryRepository implements WidgetRepository {

    private final SortedMap<Integer, Widget> widgetMap = new TreeMap<>();

    @Override
    public Widget findFirstByOrderByZzIndexDesc() {
        if (widgetMap.isEmpty()) {
            return null;
        }
        return widgetMap.get(widgetMap.lastKey());
    }

    @Override
    public Widget findByZzIndex(Integer zIndex) {
        return widgetMap.get(zIndex);
    }

    @Override
    public List<Widget> findByZzIndexGreaterThanEqual(Integer zIndex) {
        return new ArrayList<>(widgetMap.tailMap(zIndex).values());
    }

    @Override
    public List<Widget> findByOrderByZzIndexAsc() {
        return new ArrayList<>(widgetMap.values());
    }

    @Override
    public Widget save(Widget widget) {
        widgetMap.put(widget.getZzIndex(), widget);
        return widget;
    }

    @Override
    public Optional<Widget> findById(UUID uuid) {
        return widgetMap.values().stream().filter(widget1 -> widget1.getId().equals(uuid)).findFirst();
    }

    @Override
    public void delete(Widget widget) {
        widgetMap.remove(widget.getZzIndex());
    }

    public SortedMap<Integer, Widget> getWidgetMap() {
        return widgetMap;
    }
}
