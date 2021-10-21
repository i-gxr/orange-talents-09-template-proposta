package br.com.zup.proposta.controllers;

import br.com.zup.proposta.controllers.exceptions.*;
import br.com.zup.proposta.controllers.requests.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import javax.validation.*;
import java.net.*;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository repository;

    @Autowired
    private PaisRepository paisRepository;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid EstadoRequest request) {
        if (repository.existsByNomeAndPaisId(request.getNome(), request.getPaisId()))
            throw new EstadoJaExistenteException();

        Pais pais = paisRepository.findById(request.getPaisId()).orElseThrow(PaisNaoEncontradoException::new);
        Estado estado = request.toModel(pais);
        repository.save(estado);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pais.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
