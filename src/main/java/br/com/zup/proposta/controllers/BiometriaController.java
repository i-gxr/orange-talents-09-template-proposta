package br.com.zup.proposta.controllers;

import br.com.zup.proposta.commons.validations.*;
import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import br.com.zup.proposta.requests.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import javax.validation.*;
import java.net.*;

@RestController
@RequestMapping("/api/biometrias")
public class BiometriaController {

    @Autowired
    private BiometriaRepository repository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private FingerprintValidator fingerprintValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(fingerprintValidator);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestParam String numeroCartao, @RequestBody @Valid BiometriaRequest request) {
        Cartao cartao = cartaoRepository.findById(numeroCartao).orElseThrow(CartaoNaoEncontradoException::new);
        Biometria biometria = request.toModel(cartao);
        repository.save(biometria);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(biometria.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
