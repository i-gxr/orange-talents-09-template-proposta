package br.com.zup.proposta.controllers;

import br.com.zup.proposta.controllers.requests.*;
import br.com.zup.proposta.models.*;
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
class EstadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstadoRepository repository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private Gson gson;

    private String uri;
    private EstadoRequest estadoRequest;
    private Pais pais;

    @BeforeEach
    void setUp() {
        pais = new PaisRequest("Brasil").toModel();
        paisRepository.save(pais);

        this.uri = "/api/estados";
        this.estadoRequest = new EstadoRequest("São Paulo", pais.getId());
    }

    @Test
    void insertShouldReturnCreatedStatusWhenEstadoIsValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(estadoRequest))
        ).andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    void insertShouldReturnBadRequestStatusWhenNameFromEstadoIsNull() throws Exception {
        EstadoRequest estadoRequestInvalid = new EstadoRequest(null, pais.getId());
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(estadoRequestInvalid))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void insertShouldReturnBadRequestStatusWhenPaisFromEstadoNotFound() throws Exception {
        EstadoRequest estadoRequestInvalid = new EstadoRequest("São Paulo", 1000L);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(estadoRequestInvalid))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void insertShouldReturnBadRequestStatusWhenPaisFromEstadoIsNull() throws Exception {
        EstadoRequest estadoRequestInvalid = new EstadoRequest("São Paulo", null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(estadoRequestInvalid))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void insertShouldReturnUnprocessableEntityStatusWhenEstadoAlreadyInserted() throws Exception {
        repository.save(estadoRequest.toModel(pais));
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(estadoRequest))
        ).andExpect(MockMvcResultMatchers.status().is(422));
    }

}