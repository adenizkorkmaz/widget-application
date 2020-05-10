package com.task.widget.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private Integer entityId;

    public NotFoundException(String message, Integer entityId) {
        super(message);
        this.entityId = entityId;
    }


}
