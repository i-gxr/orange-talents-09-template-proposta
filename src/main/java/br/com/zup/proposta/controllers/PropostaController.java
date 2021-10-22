package br.com.zup.proposta.controllers;

import br.com.zup.proposta.commons.validations.*;
import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import br.com.zup.proposta.services.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import javax.transaction.*;
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

    @Autowired
    private ConsultaFinanceiraService consultaFinanceiraService;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(cpfOrCnpjValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> insert(@RequestBody @Valid PropostaRequest request) {
        if (repository.existsByDocumento(request.getDocumento()))
            throw new DocumentoJaExistenteException();

        Estado estado = estadoRepository.findById(request.getEstadoId()).orElseThrow(EstadoNaoEncontradoException::new);
        Proposta proposta = request.toModel(estado);
        repository.save(proposta);

        AnaliseResponse analiseResponse = consultaFinanceiraService.postAnaliseRequest(
                new AnaliseRequest(proposta.getDocumento(), proposta.getNome(), proposta.getId().toString()));

        proposta.setStatusProposta(analiseResponse);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
