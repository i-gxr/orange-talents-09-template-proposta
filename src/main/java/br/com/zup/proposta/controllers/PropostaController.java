package br.com.zup.proposta.controllers;

import br.com.zup.proposta.commons.validations.*;
import br.com.zup.proposta.controllers.exceptions.*;
import br.com.zup.proposta.controllers.requests.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import javax.validation.*;
import java.net.*;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CpfOrCnpjValidator cpfOrCnpjValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(cpfOrCnpjValidator);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid PropostaRequest request) {
        Estado estado = estadoRepository.findById(request.getEstadoId()).orElseThrow(EstadoNaoEncontradoException::new);
        Proposta proposta = request.toModel(estado);
        repository.save(proposta);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
