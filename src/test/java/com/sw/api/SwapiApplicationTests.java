package com.sw.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.api.domain.entity.PlanetEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class SwapiApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void cleanUp() {
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }


    @DisplayName("given object to save"
            + " when save object using MongoDB template"
            + " then object is saved")
    @Test
    void testRoutePostPlanet() throws Exception {

        var planet = new PlanetEntity();
        planet.setName("okokok");
        planet.setClimate("okokok-climate");
        planet.setGround("okokok-ground");
        planet.setId("okokok-ground");
        planet.setQtdFilms(0);

        this.getMockMvc().perform(
                post("/planet").contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsString(planet))
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect( jsonPath("$.id").value(Matchers.notNullValue()) )
                .andExpect( jsonPath("$.climate").value("okokok-climate") )
                .andExpect( jsonPath("$.ground").value("okokok-ground") );
               // .andExpect( jsonPath("$.qtdFilms").value( Matchers.greaterThan(0)) );
    }

}
