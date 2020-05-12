package com.task.widget.service;

import com.task.widget.exception.NotFoundException;
import com.task.widget.model.Widget;
import com.task.widget.model.WidgetDto;
import com.task.widget.model.WidgetSearchDto;
import com.task.widget.repository.WidgetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WidgetServiceTest {

    @InjectMocks
    private WidgetService widgetService;

    @Mock
    private WidgetRepository widgetRepository;

    @Test
    void create_withExistingZIndex_shouldCreateSuccessfully() {
        WidgetDto widgetDto = WidgetDto.builder()
                .z(1)
                .build();

        Widget foundWidget = Widget.builder().zzIndex(1).build();
        when(widgetRepository.findByZzIndex(1)).thenReturn(foundWidget);
        when(widgetRepository.findByZzIndexGreaterThanEqual(1)).thenReturn(Collections.singletonList(foundWidget));

        widgetService.create(widgetDto);

        assertEquals(2, foundWidget.getZzIndex());
        verify(widgetRepository, times(2)).save(any(Widget.class));
    }

    @Test
    void create_withZIndex_shouldCreateSuccessfully() {
        WidgetDto widgetDto = WidgetDto.builder()
                .z(1)
                .build();

        when(widgetRepository.findByZzIndex(1)).thenReturn(null);

        ArgumentCaptor<Widget> widgetArgumentCaptor = ArgumentCaptor.forClass(Widget.class);

        widgetService.create(widgetDto);

        verify(widgetRepository, times(1)).save(widgetArgumentCaptor.capture());
        Widget value = widgetArgumentCaptor.getValue();
        assertEquals(1, value.getZzIndex());
        assertNotNull(value.getId());
        assertNotNull(value.getLastModificationDate());
        verifyNoMoreInteractions(widgetRepository);
    }

    @Test
    void create_withoutZIndex_shouldCreateSuccessfully() {
        WidgetDto widgetDto = WidgetDto.builder()
                .build();

        Widget found = Widget.builder().zzIndex(5).build();
        when(widgetRepository.findFirstByOrderByZzIndexDesc()).thenReturn(found);

        ArgumentCaptor<Widget> widgetArgumentCaptor = ArgumentCaptor.forClass(Widget.class);

        widgetService.create(widgetDto);

        verify(widgetRepository, times(1)).save(widgetArgumentCaptor.capture());
        Widget value = widgetArgumentCaptor.getValue();
        assertEquals(6, value.getZzIndex());
        assertNotNull(value.getId());
        assertNotNull(value.getLastModificationDate());
        verifyNoMoreInteractions(widgetRepository);
    }

    @Test
    void update_shouldUpdateSuccesfully() {
        WidgetDto widgetDto = WidgetDto.builder().build();
        UUID id = UUID.randomUUID();

        Widget found = Widget.builder().build();
        when(widgetRepository.findById(id)).thenReturn(Optional.of(found));

        widgetService.update(id, widgetDto);

        verify(widgetRepository).delete(found);
    }

    @Test
    void update_shouldThrowNotFoundException_whenWidgetNotFound() {
        WidgetDto widgetDto = WidgetDto.builder().build();
        UUID id = UUID.randomUUID();

        when(widgetRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> widgetService.update(id, widgetDto));
    }

    @Test
    void findAll() {
        List<Widget> widgetList = Arrays.asList(Widget.builder().build(), Widget.builder().build());
        when(widgetRepository.findByOrderByZzIndexAsc()).thenReturn(widgetList);

        Page<Widget> all = widgetService.findAll(
                PageRequest.of(0, 5), WidgetSearchDto.builder().build());

        assertEquals(all.getContent(), widgetList);
    }

    @Test
    void findById_shouldFindSuccessfully() {
        UUID id = UUID.randomUUID();
        Widget widget = Widget.builder().build();
        when(widgetRepository.findById(id)).thenReturn(Optional.of(widget));

        Widget byId = widgetService.findById(id);

        assertEquals(byId, widget);
    }

    @Test
    void findById_shouldThrowNotFoundException() {
        UUID id = UUID.randomUUID();
        when(widgetRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> widgetService.findById(id));
    }

    @Test
    void delete() {
        UUID id = UUID.randomUUID();
        Widget widget = Widget.builder().build();
        when(widgetRepository.findById(id)).thenReturn(Optional.of(widget));

        widgetService.delete(id);

        verify(widgetRepository).delete(widget);
    }
}
