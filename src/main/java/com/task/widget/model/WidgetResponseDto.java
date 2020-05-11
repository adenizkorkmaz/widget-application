package com.task.widget.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WidgetResponseDto extends PagedModel<WidgetResponseDto> {
    private UUID id;

    private Integer x;

    private Integer y;

    private Integer z;

    private Integer width;

    private Integer height;

    private LocalDateTime lastModificationDate;

}
