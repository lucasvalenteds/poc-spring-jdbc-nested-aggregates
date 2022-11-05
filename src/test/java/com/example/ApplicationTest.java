package com.example;

import com.example.incident.Incident;
import com.example.incident.IncidentRepository;
import com.example.intervention.InterventionType;
import com.example.intervention.InterventionTypeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTest.class);

    @Container
    private static final PostgreSQLContainer<?> CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres"));

    @DynamicPropertySource
    private static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private InterventionTypeRepository interventionTypeRepository;

    private static InterventionType phoneCall;
    private static InterventionType enqueue;
    private static Incident incident;

    @AfterEach
    public void afterEach(ApplicationContext context) throws JsonProcessingException {
        if (incident != null) {
            final var objectMapper = context.getBean(ObjectMapper.class);
            final var incidentFoundById = incidentRepository.findById(incident.getId());
            final var incidentFoundByIdSerializedToJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(incidentFoundById.orElseThrow());

            LOGGER.atInfo().log(incidentFoundByIdSerializedToJson);
        }
    }

    @Test
    @Order(1)
    void findingInterventionTypesByName() {
        phoneCall = interventionTypeRepository.findByName("PHONE_CALL").orElseThrow();
        assertThat(phoneCall).isNotNull();
        assertThat(phoneCall.getId()).isNotNull();
        assertThat(phoneCall.getName()).isEqualTo("PHONE_CALL");

        enqueue = interventionTypeRepository.findByName("ENQUEUE").orElseThrow();
        assertThat(enqueue).isNotNull();
        assertThat(enqueue.getId()).isNotNull();
        assertThat(enqueue.getName()).isEqualTo("ENQUEUE");

        final var unknown = interventionTypeRepository.findByName("UNKNOWN");
        assertThat(unknown).isNotPresent();
    }

    @Test
    @Order(2)
    void creatingIncident() {
        final var description = "Customer are unable to login";
        final var createdAt = Instant.now(Clock.systemUTC());

        incident = incidentRepository.save(Incident.builder()
                .description(description)
                .createdAt(createdAt)
                .build());

        assertThat(incident).isNotNull();
        assertThat(incident.getId()).isEqualTo(1L);
        assertThat(incident.getDescription()).isEqualTo(description);
        assertThat(incident.getCreatedAt()).isEqualTo(createdAt);
    }
}
