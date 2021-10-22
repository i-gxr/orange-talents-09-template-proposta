package br.com.zup.proposta.controllers;

import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import javax.validation.*;
import java.net.*;

@RestController
@RequestMapping("/api/paises")
public class PaisController {

    @Autowired
    private PaisRepository repository;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid PaisRequest request) {
        if (repository.existsByNome(request.getNome()))
            throw new PaisJaExistenteException();

        Pais pais = request.toModel();
        repository.save(pais);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pais.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
