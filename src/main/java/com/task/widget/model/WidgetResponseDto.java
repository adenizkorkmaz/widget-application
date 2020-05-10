package com.task.widget.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.PagedModel;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WidgetResponseDto extends PagedModel<WidgetResponseDto> {
    private UUID id;

    private Integer x;

    private Integer y;

    private Integer z;

    private Integer width;

    private Integer height;

    private LocalDateTime lastModificationDate;

}
