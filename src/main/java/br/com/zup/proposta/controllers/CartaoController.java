package br.com.zup.proposta.controllers;

import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import br.com.zup.proposta.requests.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.*;
import javax.websocket.server.*;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    @Autowired
    private CartaoRepository repository;

    @PostMapping("/bloqueios")
    public void blockCard(@RequestHeader(value = "User-Agent") String userAgent, @RequestParam String numeroCartao, HttpServletRequest request) {
        Cartao cartao = repository.findById(numeroCartao).orElseThrow(CartaoNaoEncontradoException::new);
        BloqueioCartao bloqueioCartao = new BloqueioCartao(userAgent, request.getRemoteAddr());
        cartao.addBloqueios(bloqueioCartao);
        repository.save(cartao);
    }

}
