package br.com.zup.proposta.services;

import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@FeignClient(name = "associacaoCartaoService", url = "http://localhost:8888/api/cartoes")
public interface AssociacaoCartaoService {

    @PostMapping(consumes = "application/json")
    AssociacaoCartaoResponse postAssociacaoCartaoRequest(@RequestBody @Valid AssociacaoCartaoRequest request);

}
