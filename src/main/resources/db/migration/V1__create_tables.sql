CREATE SEQUENCE intervention_id_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE intervention
(
    intervention_id   INTEGER DEFAULT nextval('intervention_id_sequence'),
    intervention_name VARCHAR,

    CONSTRAINT pk_intervention_id PRIMARY KEY (intervention_id),
    CONSTRAINT uq_intervention_name UNIQUE (intervention_name)
);

INSERT INTO intervention (intervention_name)
VALUES ('PHONE_CALL'),
       ('ENQUEUE');



CREATE SEQUENCE incident_id_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE incident
(
    incident_id          INTEGER DEFAULT nextval('incident_id_sequence'),
    incident_description VARCHAR,
    incident_created_at  TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_incident_id PRIMARY KEY (incident_id)
);



CREATE SEQUENCE intervention_intervention_id_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE incident_intervention
(
    incident_id                      INTEGER,
    incident_intervention_id         INTEGER DEFAULT nextval('intervention_intervention_id_sequence'),
    incident_intervention_type_id    INTEGER,
    incident_intervention_type_name  VARCHAR,
    incident_intervention_created_at TIMESTAMP WITH TIME ZONE,
    incident_intervention_key        INTEGER,

    CONSTRAINT pk_incident_intervention_id PRIMARY KEY (incident_intervention_id),
    CONSTRAINT fk_incident_id FOREIGN KEY (incident_id) REFERENCES incident (incident_id),
    CONSTRAINT fk_incident_intervention_type_id FOREIGN KEY (incident_intervention_type_id) REFERENCES intervention (intervention_id)
);
