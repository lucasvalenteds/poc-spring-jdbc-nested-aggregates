package com.example.incident;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @MappedCollection(idColumn = "incident_id", keyColumn = "incident_intervention_key")
    private List<Intervention> interventions;

    public void addIntervention(Intervention intervention) {
        this.interventions = Optional.ofNullable(this.interventions)
                .orElseGet(() -> new ArrayList<>(1));

        this.interventions.add(intervention);
    }
}
