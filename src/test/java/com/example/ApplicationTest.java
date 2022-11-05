package com.example;

import com.example.intervention.InterventionType;
import com.example.intervention.InterventionTypeRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTest {

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
    private InterventionTypeRepository interventionTypeRepository;

    private static InterventionType phoneCall;
    private static InterventionType enqueue;

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
}
