package br.com.zup.proposta.services;

import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@FeignClient(name = "associarCarteiraCartaoService", url = "${proposta.external-api-cartoes-url}")
public interface AssociarCarteiraCartaoService {

    @PostMapping(value = "/{id}/carteiras", consumes = "application/json")
    AssociarCarteiraCartaoResponse postAssociarCarteiraCartaoRequest(@PathVariable String id, @RequestBody @Valid AssociarCarteiraCartaoRequest request);

}
