package com.task.widget.repository;

import com.task.widget.model.Widget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WidgetInMemoryRepositoryTest {

    WidgetInMemoryRepository repository = new WidgetInMemoryRepository();
    Widget widget;
    Widget widget2;

    @BeforeEach
    public void init() {
        repository.getWidgets().clear();

        widget = Widget.builder()
                .id(UUID.randomUUID())
                .x(2).y(3)
                .zzIndex(1).build();

        widget2 = Widget.builder()
                .id(UUID.randomUUID())
                .x(2).y(3)
                .zzIndex(2).build();

        repository.save(widget);
        repository.save(widget2);
    }

    @Test
    void save() {
        Widget widget = Widget.builder()
                .x(2)
                .y(3)
                .zzIndex(100).build();

        Widget save = repository.save(widget);

        assertTrue(repository.getWidgets().contains(widget));
        assertEquals(save, widget);
    }

    @Test
    void findFirstByOrderByZzIndexDesc() {
        Widget w = repository.findFirstByOrderByZzIndexDesc();
        assertEquals(widget2.getZzIndex(), w.getZzIndex());
    }

    @Test
    void findByZzIndex() {
        Widget w = repository.findByZzIndex(1);
        assertEquals(w, widget);
    }

    @Test
    void findByZzIndexGreaterThanEqual() {
        List<Widget> widgets = repository.findByZzIndexGreaterThanEqual(1);

        assertThat(widgets, hasSize(2));
        assertThat(widgets, hasItems(widget, widget2));
    }

    @Test
    void findByOrderByZzIndexAsc() {
        List<Widget> widgetList = repository.findByOrderByZzIndexAsc();
        assertEquals(widgetList.get(0), widget);
        assertEquals(widgetList.get(1), widget2);
    }

    @Test
    void findById() {
        Optional<Widget> byId = repository.findById(widget.getId());
        assertEquals(byId.get(), widget);
    }

    @Test
    void delete() {
        repository.delete(widget);
        ConcurrentSkipListSet<Widget> widgets = repository.getWidgets();
        assertEquals(1, widgets.size());
        assertEquals(widget2, widgets.iterator().next());
    }
}
