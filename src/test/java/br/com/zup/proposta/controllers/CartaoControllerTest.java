package br.com.zup.proposta.controllers;

import br.com.zup.proposta.commons.utils.*;
import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import br.com.zup.proposta.services.*;
import com.google.gson.*;
import feign.*;
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
import java.time.*;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class CartaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartaoRepository repository;

    @MockBean
    private SolicitacaoBloqueioCartaoService solicitacaoBloqueioCartaoService;

    @Autowired
    private Gson gson;

    private String uri;

    @BeforeEach
    void setUp() {
        this.uri = "/api/cartoes";
    }

    @Test
    void blockCardShouldReturnOkStatusWhenBloqueioCartaoIsValid() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        String numeroCartao = "1921-1063-9349-1322";
        Cartao cartao = Mockito.mock(Cartao.class);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(cartao));
        Mockito.when(solicitacaoBloqueioCartaoService.postSolicitacaoBloqueioCartaoRequest(Mockito.any(), Mockito.any())).thenReturn(new SolicitacaoBloqueioCartaoResponse("BLOQUEADO"));
        Mockito.when(repository.save(Mockito.any())).thenReturn(Mockito.any(Cartao.class));

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/bloqueios")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .header("User-Agent", userAgent)
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void blockCardShouldReturnNotFoundStatusWhenNumeroCartaoDoesNotExist() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        String numeroCartao = "1921-1063-9349-1322";

        Mockito.when(repository.findById(Mockito.any())).thenThrow(CartaoNaoEncontradoException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/bloqueios")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .header("User-Agent", userAgent)
        ).andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void blockCardShouldReturnUnprocessableEntityStatusWhenCartaoHasActiveBloqueio() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        String numeroCartao = "1921-1063-9349-1322";
        Cartao cartao = Mockito.mock(Cartao.class);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(cartao));
        Mockito.when(cartao.isBloqueado()).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/bloqueios")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .header("User-Agent", userAgent)
        ).andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    void blockCardShouldReturnBadRequestWhenNotFoundUserAgentHeader() throws Exception {
        String numeroCartao = "1921-1063-9349-1322";

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/bloqueios")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void blockCardShouldReturnBadRequestWhenNotFoundNumeroCartaoParam() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/bloqueios")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", userAgent)
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void blockCardShouldReturnInternalServerErrorWhenSolicitacaoBloqueioCartaoServiceNotAvailable() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        String numeroCartao = "1921-1063-9349-1322";
        Cartao cartao = Mockito.mock(Cartao.class);
        FeignException feignException = Mockito.mock(FeignException.class);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(cartao));
        Mockito.when(solicitacaoBloqueioCartaoService.postSolicitacaoBloqueioCartaoRequest(Mockito.any(), Mockito.any())).thenThrow(feignException);

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/bloqueios")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .header("User-Agent", userAgent)
        ).andExpect(MockMvcResultMatchers.status().is(500));
    }

    @Test
    void notifyTravelShouldReturnOkStatusWhenAvisoCartaoIsValid() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        String numeroCartao = "1921-1063-9349-1322";
        Cartao cartao = Mockito.mock(Cartao.class);
        AvisoCartaoRequest request = new AvisoCartaoRequest(LocalDate.now(), "Campos do Jordão");

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(cartao));
        Mockito.when(repository.save(Mockito.any())).thenReturn(Mockito.any(Cartao.class));

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/avisos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .header("User-Agent", userAgent)
                .characterEncoding("utf-8")
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void notifyTravelShouldReturnBadRequestStatusWhenDataViagemIsPast() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        String numeroCartao = "1921-1063-9349-1322";
        AvisoCartaoRequest request = new AvisoCartaoRequest(LocalDate.now().minusDays(1), "Campos do Jordão");

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/avisos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .header("User-Agent", userAgent)
                .characterEncoding("utf-8")
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @ParameterizedTest
    @NullSource
    void notifyTravelShouldReturnBadRequestStatusWhenDataViagemIsNull(LocalDate date) throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        String numeroCartao = "1921-1063-9349-1322";
        AvisoCartaoRequest request = new AvisoCartaoRequest(date, "Campos do Jordão");

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/avisos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .header("User-Agent", userAgent)
                .characterEncoding("utf-8")
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void notifyTravelShouldReturnBadRequestStatusWhenDataViagemIsNull(String destino) throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        String numeroCartao = "1921-1063-9349-1322";
        AvisoCartaoRequest request = new AvisoCartaoRequest(LocalDate.now(), destino);

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/avisos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .header("User-Agent", userAgent)
                .characterEncoding("utf-8")
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void notifyTravelShouldReturnNotFoundStatusWhenNumeroCartaoDoesNotExist() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        String numeroCartao = "1921-1063-9349-1322";
        AvisoCartaoRequest request = new AvisoCartaoRequest(LocalDate.now().plusDays(1), "Campos do Jordão");

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        Mockito.when(repository.findById(Mockito.any())).thenThrow(CartaoNaoEncontradoException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/avisos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .header("User-Agent", userAgent)
                .characterEncoding("utf-8")
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void notifyTravelShouldReturnBadRequestWhenNotFoundUserAgentHeader() throws Exception {
        String numeroCartao = "1921-1063-9349-1322";
        AvisoCartaoRequest request = new AvisoCartaoRequest(LocalDate.now().plusDays(1), "Campos do Jordão");

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/avisos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
                .characterEncoding("utf-8")
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void notifyTravelShouldReturnBadRequestWhenNotFoundNumeroCartaoParam() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";
        AvisoCartaoRequest request = new AvisoCartaoRequest(LocalDate.now().plusDays(1), "Campos do Jordão");

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/avisos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", userAgent)
                .characterEncoding("utf-8")
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

}
