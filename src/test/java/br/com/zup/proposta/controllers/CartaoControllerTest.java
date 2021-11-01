package br.com.zup.proposta.controllers;

import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import br.com.zup.proposta.responses.*;
import br.com.zup.proposta.services.*;
import feign.*;
import org.junit.jupiter.api.*;
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

    private String uri;

    @BeforeEach
    void setUp() {
        this.uri = "/api/cartoes";
    }

    @Test
    void insertShouldReturnOkStatusWhenBloqueioCartaoIsValid() throws Exception {
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
    void insertShouldReturnNotFoundStatusWhenNumeroCartaoDoesNotExist() throws Exception {
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
    void insertShouldReturnUnprocessableEntityStatusWhenCartaoHasActiveBloqueio() throws Exception {
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
    void insertShouldReturnBadRequestWhenNotFoundUserAgentHeader() throws Exception {
        String numeroCartao = "1921-1063-9349-1322";

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/bloqueios")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroCartao", numeroCartao)
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void insertShouldReturnBadRequestWhenNotFoundNumeroCartaoParam() throws Exception {
        String userAgent = "PostmanRuntime/7.28.4";

        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/bloqueios")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", userAgent)
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void insertShouldReturnInternalServerErrorWhenSolicitacaoBloqueioCartaoServiceNotAvailable() throws Exception {
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

}
