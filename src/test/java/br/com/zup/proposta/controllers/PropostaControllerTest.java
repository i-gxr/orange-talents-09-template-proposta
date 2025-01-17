package br.com.zup.proposta.controllers;

import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.models.enums.*;
import br.com.zup.proposta.repositories.*;
import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import br.com.zup.proposta.services.*;
import com.google.gson.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;

import javax.transaction.*;
import java.math.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class PropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PaisRepository paisRepository;

    @MockBean
    private CartaoService cartaoService;

    @MockBean
    private ConsultaFinanceiraService consultaFinanceiraService;

    @Autowired
    private Gson gson;

    private String uri;
    private Estado estado;

    @BeforeEach
    void setUp() {
        Pais pais = new PaisRequest("Brasil").toModel();
        paisRepository.save(pais);

        EstadoRequest estadoRequest = new EstadoRequest("São Paulo", 1L);
        this.estado = estadoRequest.toModel(pais);
        estadoRepository.save(this.estado);

        this.uri = "/api/propostas";
    }

    @Test
    void insertShouldReturnCreatedStatusWhenPropostaIsValid() throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "51304154009", "igor@email.com", new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        AnaliseResponse analiseResponse = new AnaliseResponse("51304154009", "Igor Silva", "SEM_RESTRICAO", "1");
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(analiseResponse);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(201)
        ).andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://localhost/api/propostas/{spring:[0-9]+}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"51304154000", "80273083000120"})
    void insertShouldReturnBadRequestStatusWhenDocumentoFromPropostaDoesNotValid(String documento) throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", documento, "igor@email.com", new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void insertShouldReturnUnprocessableEntityStatusWhenDocumentoAlreadyInserted() throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "51304154009", "igor@email.com", new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        repository.save(propostaRequest.toModel(estado));
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(422));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void insertShouldReturnBadRequestStatusWhenDocumentoIsNullOrEmpty(String documento) throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", documento, "igor@email.com", new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @ParameterizedTest
    @ValueSource(strings = {"igor", "igor@", "igor.com"})
    void insertShouldReturnBadRequestStatusWhenEmailFromPropostaDoesNotValid(String email) throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "51304154009", email, new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void insertShouldReturnBadRequestStatusWhenEmailFromPropostaIsNullOrEmpty(String email) throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "51304154009", email, new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void insertShouldReturnBadRequestStatusWhenNomeFromPropostaIsNullOrEmpty(String nome) throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest(nome, "51304154009", "igor@email.com", new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void insertShouldReturnBadRequestStatusWhenEnderecoFromPropostaIsNullOrEmpty(String endereco) throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "51304154009", "igor@email.com", new BigDecimal("2500.00"), endereco, "Poá", estado.getId());
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-2500", "-1"})
    void insertShouldReturnBadRequestStatusWhenSalarioFromPropostaIsNegative(String salario) throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "51304154009", "igor@email.com", new BigDecimal(salario), "R. Zup, 66", "Poá", estado.getId());
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void insertShouldReturnBadRequestStatusWhenSalarioFromPropostaIsNull() throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "51304154009", "igor@email.com", null, "R. Zup, 66", "Poá", estado.getId());
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void insertShouldReturStatusPropostaElegivelWhenDocumentoHasNoRestriction() throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "11130223027", "igor@email.com", new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        AnaliseResponse analiseResponse = new AnaliseResponse("11130223027", "Igor Silva", "SEM_RESTRICAO", "1");
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(analiseResponse);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest)));
        Proposta proposta = repository.findByDocumento(propostaRequest.getDocumento()).get();
        Assertions.assertEquals(StatusProposta.ELEGIVEL, proposta.getStatusProposta());
    }

    @Test
    void insertShouldReturStatusPropostaNaoElegivelWhenDocumentoHasRestriction() throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "34874240020", "igor@email.com", new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        AnaliseResponse analiseResponse = new AnaliseResponse("11130223027", "Igor Silva", "COM_RESTRICAO", "1");
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(analiseResponse);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(propostaRequest)));
        Proposta proposta = repository.findByDocumento(propostaRequest.getDocumento()).get();
        Assertions.assertEquals(StatusProposta.NAO_ELEGIVEL, proposta.getStatusProposta());
    }

    @Test
    void findByIdShouldReturnOkStatusAndBodyWhenIdFromPropostaExisting() throws Exception {
        PropostaRequest propostaRequest = new PropostaRequest("Igor Silva", "51304154009", "igor@email.com", new BigDecimal("2500.00"), "R. Zup, 66", "Poá", estado.getId());
        AnaliseResponse analiseResponse = new AnaliseResponse("51304154009", "Igor Silva", "SEM_RESTRICAO", "1");
        Mockito.when(consultaFinanceiraService.postAnaliseRequest(Mockito.any())).thenReturn(analiseResponse);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(propostaRequest)));

        Proposta proposta = repository.findByDocumento("51304154009").orElseThrow(PropostaNaoEncontradaException::new);
        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/" + proposta.getId()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(gson.toJson(proposta.toResponse())));
    }

    @Test
    void findByIdShouldReturnNotFoundStatusWhenIdFromPropostaDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/" + 1000L))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

}