package com.task.widget.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ErrorDto> handleNotFound(NotFoundException ex) {
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .timestamp(System.currentTimeMillis())
                .errorMessages(Collections.singletonList(ex.getMessage() + " Id : " + ex.getEntityId()))
                .build();
        log.error("BadRequestException: " + errorDto.toString());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = BadRequestException.class)
    protected ResponseEntity<ErrorDto> handleBadRequest(BadRequestException ex) {
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .timestamp(System.currentTimeMillis())
                .errorMessages(Collections.singletonList(ex.getMessage()))
                .build();
        log.error("BadRequestException: " + errorDto.toString());
        return ResponseEntity.badRequest().body(
                errorDto);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .timestamp(System.currentTimeMillis())
                .errorMessages(errorMessages)
                .build();
        log.error("BadRequestException: " + errorDto.toString());
        return ResponseEntity.badRequest().body(
                errorDto);
    }
}
