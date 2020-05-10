package com.task.widget.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDto {
    private List<String> errorMessages;
    private int status;
    private String error;
    private Long timestamp;
}
