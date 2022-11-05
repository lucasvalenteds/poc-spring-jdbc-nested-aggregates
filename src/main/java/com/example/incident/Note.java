package com.example.incident;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("incident_intervention_note")
@Data
@Builder
public class Note {

    @Column("incident_intervention_note_content")
    private String content;

    @Column("incident_intervention_note_created_at")
    private Instant createdAt;
}
