package br.com.zup.proposta.controllers;

import br.com.zup.proposta.controllers.requests.*;
import br.com.zup.proposta.repositories.*;
import com.google.gson.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;

import javax.transaction.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PaisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaisRepository repository;

    @Autowired
    private Gson gson;

    private String uri;
    private PaisRequest paisRequest;

    @BeforeEach
    void setUp() {
        this.uri = "/api/paises";
        this.paisRequest = new PaisRequest("Brasil");
    }

    @Test
    void insertShouldReturnCreatedStatusWhenPaisIsValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(paisRequest))
        ).andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    void insertShouldReturnUnprocessableEntityStatusWhenExistingPais() throws Exception {
        repository.save(paisRequest.toModel());
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(paisRequest))
        ).andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    void insertShouldReturnBadRequestStatusWhenPaisIsNull() throws Exception {
        PaisRequest paisRequestNull = new PaisRequest(null);
        String json = gson.toJson(paisRequestNull);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

}