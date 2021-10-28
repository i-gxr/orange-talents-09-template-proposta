package br.com.zup.proposta.controllers;

import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import br.com.zup.proposta.requests.*;
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
import java.time.*;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BiometriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private BiometriaRepository repository;

    @Autowired
    private CartaoRepository cartaoRepository;

    private Cartao cartao;
    private String uri;

    @BeforeEach
    void setUp() {
        this.cartao = new Cartao("1", LocalDateTime.now(), "Igor Silva", Set.of(), Set.of(), Set.of(), Set.of(), 1000.00, null, null, null);
        this.uri = "/api/biometrias";
    }

    @Test
    void insertShouldReturnCreatedStatusWhenBiometriaIsValidAndNumeroCartaoExist() throws Exception {
        cartaoRepository.save(cartao);
        BiometriaRequest request = new BiometriaRequest("dGVzdGU=");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
                .param("numeroCartao", cartao.getNumeroCartao())
        ).andExpect(MockMvcResultMatchers.status().is(201)
        ).andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://localhost/api/biometrias/*"));
    }

    @Test
    void insertShouldReturnBadRequestStatusWhenBiometriaIsNotValidAndNumeroCartaoExist() throws Exception {
        cartaoRepository.save(cartao);
        BiometriaRequest request = new BiometriaRequest("Biometria Inválida!");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
                .param("numeroCartao", cartao.getNumeroCartao())
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void insertShouldReturnNotFoundStatusWhenNumeroCartaoDoesNotExistAndBiometriaIsValid() throws  Exception {
        BiometriaRequest request = new BiometriaRequest("dGVzdGU=");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
                .param("numeroCartao", "0")
        ).andExpect(MockMvcResultMatchers.status().is(404));
    }

}