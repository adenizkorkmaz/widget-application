package com.task.widget.repository;

import com.task.widget.model.Widget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WidgetRepository {
    Page<Widget> findAll(Pageable pageable);

    Widget save(Widget widget);

    Optional<Widget> findById(UUID uuid);

    void delete(Widget widget);
}
