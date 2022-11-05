package com.example.intervention;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("intervention")
@Data
public class InterventionType {

    @Id
    @Column("intervention_id")
    private Long id;

    @Column("intervention_name")
    private String name;
}
