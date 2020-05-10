package com.task.widget.repository;

import com.task.widget.model.Widget;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "storage.type", havingValue = "database")
public interface WidgetRepository extends org.springframework.data.repository.Repository<Widget, UUID> {
    Widget findFirstByOrderByZzIndexDesc();

    Widget findByZzIndex(Integer zIndex);

    List<Widget> findByZzIndexGreaterThanEqual(Integer zIndex);

    List<Widget> findByOrderByZzIndexAsc();

    Widget save(Widget widget);

    Optional<Widget> findById(UUID uuid);

    void delete(Widget widget);
}
