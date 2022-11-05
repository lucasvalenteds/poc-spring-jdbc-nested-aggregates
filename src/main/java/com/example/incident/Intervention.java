package com.example.incident;

import com.example.intervention.InterventionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @MappedCollection(idColumn = "incident_intervention_id", keyColumn = "incident_intervention_note_key")
    private List<Note> notes;

    public Intervention addNote(Note note) {
        this.notes = Optional.ofNullable(this.notes)
                .orElseGet(() -> new ArrayList<>(1));

        this.notes.add(note);
        return this;
    }
}
