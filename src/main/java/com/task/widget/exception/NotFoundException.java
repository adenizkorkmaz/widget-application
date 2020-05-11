package com.task.widget.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NotFoundException extends RuntimeException {

    private UUID entityId;

    public NotFoundException(String message, UUID entityId) {
        super(message);
        this.entityId = entityId;
    }


}
