package com.sw.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.api.domain.entity.PlanetEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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


    @DisplayName("Adicionar um planeta (com nome, clima e terreno)")
    @Test
    void testRoutePostPlanetReturnPlanet() throws Exception {

        var planet = new PlanetEntity()
         .setName("okokok")
         .setClimate("okokok-climate")
         .setGround("okokok-ground");

        this.getMockMvc().perform(
                post("/planet").contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsString(planet))
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect( jsonPath("$.id").value(Matchers.notNullValue()) )
                .andExpect( jsonPath("$.climate").value("okokok-climate") )
                .andExpect( jsonPath("$.ground").value("okokok-ground") )
                .andExpect( jsonPath("$.name").value("okokok") );
    }

  /*  @DisplayName("Para cada planeta também devemos ter a quantidade de aparições em filmes que\n" +
            "deve ser obtida pela api do Star Wars na inserção do planeta.")
    @Test
    void testRoutePostPlanetReturnQtdFilms() throws Exception {

        var planet = new PlanetEntity()
         .setName("Tatooine")
         .setClimate("arid")
         .setGround("desert");

        this.getMockMvc().perform(
                post("/planet").contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsString(planet))
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect( jsonPath("$.id").value(Matchers.notNullValue()) )
                .andExpect( jsonPath("$.climate").value("arid") )
                .andExpect( jsonPath("$.ground").value("desert") )
                .andExpect( jsonPath("$.name").value("Tatooine") )
                .andExpect( jsonPath("$.qtdFilms").value( Matchers.greaterThan(0)) );
    }*/

    @DisplayName("Listar planetas do banco de dados")
    @Test
    void testRouteGetPlanetReturnPlanetPage() throws Exception {

        var planet = new PlanetEntity()
         .setName("okokok-2")
         .setClimate("okokok-climate-2")
         .setGround("okokok-ground-2");

        this.getMockMvc().perform(
                post("/planet").contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsString(planet))
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.getMockMvc().perform(
                get("/planet").contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.totalElements").value( Matchers.greaterThan(0)) );
    }

    @DisplayName("Listar planetas da API")
    @Test
    void testRouteGetPlanetApiReturnPlanetPage() throws Exception {

        var planet = new PlanetEntity()
                .setName("okokok-2")
                .setClimate("okokok-climate-2")
                .setGround("okokok-ground-2");

        this.getMockMvc().perform(
                post("/planet").contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsString(planet))
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.getMockMvc().perform(
                get("/planet/fromapi").contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.totalElements").value( Matchers.greaterThan(0)) );
    }


    @DisplayName("Buscar por nome no banco de dados")
    @Test
    void testRouteGetPlanetByNameReturnPlanetPage() throws Exception {

        var planet = new PlanetEntity()
         .setName("okokok-3")
         .setClimate("okokok-climate-3")
         .setGround("okokok-ground-3");

        this.getMockMvc().perform(
                post("/planet").contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsString(planet))
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.getMockMvc().perform(
                get("/planet").param("name", "okokok-3").contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.totalElements").value( Matchers.greaterThan(0)) );
    }

    @DisplayName("Buscar por ID no banco de dados")
    @Test
    void testRouteGetPlanetByIdReturnPlanet() throws Exception {

        var planet = new PlanetEntity()
               .setName("okokok-4")
               .setClimate("okokok-climate-4")
               .setGround("okokok-ground-4");

        String content = this.getMockMvc().perform(
                post("/planet").contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsString(planet))
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        PlanetEntity entity = getMapper().readValue(content, PlanetEntity.class);

        assert entity.getId() != null;

        this.getMockMvc().perform(
                get("/planet/" + entity.getId() ).contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.id").value( Matchers.is(entity.getId() )) );
    }

    @DisplayName("Remover planeta")
    @Test
    void testRouteDeletePlanetById() throws Exception {

        var planet = new PlanetEntity()
           .setName("okokok-5")
           .setClimate("okokok-climate-5")
           .setGround("okokok-ground-5");

        String content = this.getMockMvc().perform(
                post("/planet").contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsString(planet))
        ).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        PlanetEntity entity = getMapper().readValue(content, PlanetEntity.class);

        assert entity.getId() != null;

        this.getMockMvc().perform(
                delete("/planet/" + entity.getId() ).contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());

        this.getMockMvc().perform(
                get("/planet/" + entity.getId() ).contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(content().string(Matchers.emptyString()))
                .andExpect(status().isNotFound());



    }

}
