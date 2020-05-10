package com.task.widget.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Widget {

    @Id
    private UUID id;

    private Integer x;

    private Integer y;

    private Integer zzIndex;

    private Integer width;

    private Integer height;

    private LocalDateTime lastModificationDate;

}
