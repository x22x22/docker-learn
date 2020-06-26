package io.x22x22.docker.learn.orderserver.entities;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

/**
 * @author Kdump
 */
@Data
public class Base {
    @Id
    private Long id;

    @Column(value = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(value = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
