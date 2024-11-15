package com.mkvbs.ingredient_management_service.integration_tests;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;


import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BasicIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("test_database")
            .withUsername("root")
            .withPassword("root")
            .withExposedPorts(5435);

    @BeforeAll
    public static void setUp() {
        postgres.setPortBindings(List.of("5435:5432"));
        postgres.start();
    }
}
