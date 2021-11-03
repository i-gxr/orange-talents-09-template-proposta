package br.com.zup.proposta.controllers;

import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import br.com.zup.proposta.services.*;
import feign.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import javax.servlet.http.*;
import javax.validation.*;
import java.net.*;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    @Autowired
    private CartaoRepository repository;

    @Autowired
    private SolicitacaoBloqueioCartaoService solicitacaoBloqueioCartaoService;

    @Autowired
    private NotificarAvisoViagemService notificarAvisoViagemService;

    @Autowired
    private AssociarCarteiraCartaoService associarCarteiraCartaoService;

    @PostMapping("/bloqueios")
    public void blockCard(@RequestHeader(value = "User-Agent") String userAgent, @RequestParam String numeroCartao, HttpServletRequest request) {
        Cartao cartao = repository.findById(numeroCartao).orElseThrow(CartaoNaoEncontradoException::new);
        BloqueioCartao bloqueioCartao = new BloqueioCartao(userAgent, request.getRemoteAddr());

        if (cartao.isBloqueado())
            throw new CartaoBloqueadoException();

        try {
            SolicitacaoBloqueioCartaoResponse response = solicitacaoBloqueioCartaoService.postSolicitacaoBloqueioCartaoRequest(numeroCartao, new SolicitacaoBloqueioCartaoRequest(userAgent));
            if (response.getResultado().equalsIgnoreCase("BLOQUEADO"))
                cartao.addBloqueios(bloqueioCartao);
        }
            catch (FeignException.UnprocessableEntity e) {
                throw new CartaoBloqueadoException();
            }
            catch (FeignException e) {
                throw new ServicoNaoDisponivelException();
            }

        repository.save(cartao);
    }

    @PostMapping("/avisos")
    public void notifyTravel(@RequestHeader(value = "User-Agent") String userAgent, @RequestParam String numeroCartao, @RequestBody @Valid AvisoCartaoRequest request, HttpServletRequest httpRequest) {
        Cartao cartao = repository.findById(numeroCartao).orElseThrow(CartaoNaoEncontradoException::new);
        AvisoCartao avisoCartao = request.toModel(userAgent, httpRequest.getRemoteAddr());

        try {
            NotificacaoAvisoCartaoResponse response = notificarAvisoViagemService.postNotificarAvisoViagemCartaoRequest(numeroCartao, request);
            if (response.getResultado().equalsIgnoreCase("CRIADO"))
                cartao.addAvisos(avisoCartao);
        }
            catch (FeignException.UnprocessableEntity e) {
                throw new AvisoCartaoJaInformadoException();
            }
            catch (FeignException e) {
                throw new ServicoNaoDisponivelException();
            }

        repository.save(cartao);
    }

    @PostMapping("/carteiras")
    public ResponseEntity<?> connectWallet(@RequestParam String numeroCartao, @RequestBody @Valid CarteiraCartaoRequest request) {
        Cartao cartao = repository.findById(numeroCartao).orElseThrow(CartaoNaoEncontradoException::new);
        CarteiraCartao carteiraCartao = request.toModel();

        try {
            AssociarCarteiraCartaoResponse response = associarCarteiraCartaoService.postAssociarCarteiraCartaoRequest(numeroCartao, request.toAssociarCarteiraCartaoRequest());
            if (response.getResultado().equalsIgnoreCase("ASSOCIADA"))
                cartao.addCarteiras(carteiraCartao);
        }
            catch (FeignException.UnprocessableEntity e) {
                throw new CarteiraCartaoJaAssociadoException();
            }
            catch (FeignException e) {
                throw new ServicoNaoDisponivelException();
            }

        repository.save(cartao);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(cartao.getCarteiraCartaoEqual(carteiraCartao).getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
