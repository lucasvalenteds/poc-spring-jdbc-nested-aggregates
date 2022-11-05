package com.example.incident;

import com.example.intervention.InterventionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("incident_intervention")
@Data
@Builder
public class Intervention {

    @Id
    @Column("incident_intervention_id")
    private Long id;

    @Column("incident_intervention_type_id")
    @JsonIgnore
    private AggregateReference<InterventionType, Long> type;

    @Column("incident_intervention_type_name")
    private String name;

    @Column("incident_intervention_created_at")
    private Instant createdAt;
}
