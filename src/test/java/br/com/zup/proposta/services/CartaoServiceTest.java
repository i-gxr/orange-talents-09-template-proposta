package br.com.zup.proposta.services;

import br.com.zup.proposta.models.*;
import br.com.zup.proposta.models.enums.*;
import br.com.zup.proposta.repositories.*;
import br.com.zup.proposta.responses.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;

import javax.transaction.*;
import java.math.*;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CartaoServiceTest {

    @Autowired
    private PropostaRepository propostaRepository;

    @MockBean
    private AssociacaoCartaoService associacaoCartaoService;

    @Autowired
    private CartaoService cartaoService;

    @Test
    void deveAssociarCartaoAUmaPropostaElegivel() {
        Proposta propostaElegivel = new Proposta("Igor Silva", "51304154009", "igor@email.com", new BigDecimal("2500.00"), "R. Zup, 66", "Poá", null);
        propostaRepository.save(propostaElegivel);
        propostaElegivel.setStatusProposta("SEM_RESTRICAO");
        Mockito.when(associacaoCartaoService.getAssociacaoCartaoRequest(propostaElegivel.getId().toString())).thenReturn(new AssociacaoCartaoResponse(UUID.randomUUID().toString(), null, null, Set.of(), Set.of(), Set.of(), Set.of(), null, null, null, null));
        cartaoService.connectPropostasWithoutCartao();
        Optional<Proposta> optionalProposta = propostaRepository.findById(propostaElegivel.getId());
        Assertions.assertNotNull(optionalProposta.get().getCartao());
    }

}