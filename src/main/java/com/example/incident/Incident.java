package com.example.incident;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("incident")
@Data
@Builder
public class Incident {

    @Id
    @Column("incident_id")
    private Long id;

    @Column("incident_description")
    private String description;

    @Column("incident_created_at")
    private Instant createdAt;
}
