package com.task.widget.repository;

import com.task.widget.model.Widget;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "storage.type", havingValue = "jpa")
public class WidgetJpaRepository extends SimpleJpaRepository<Widget, UUID> {

    public WidgetJpaRepository(EntityManager em) {
        super(Widget.class, em);
    }

    @Override
    public <S extends Widget> S save(S entity) {
        return super.save(entity);
    }
}
