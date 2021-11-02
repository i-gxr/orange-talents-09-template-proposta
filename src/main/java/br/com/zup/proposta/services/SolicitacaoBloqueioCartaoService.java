package br.com.zup.proposta.services;

import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@FeignClient(name = "solicitacaoBloqueioCartaoService", url = "${proposta.external-api-cartoes-url}")
public interface SolicitacaoBloqueioCartaoService {

    @PostMapping(value = "/{id}/bloqueios", consumes = "application/json")
    SolicitacaoBloqueioCartaoResponse postSolicitacaoBloqueioCartaoRequest(@PathVariable String id, @RequestBody @Valid SolicitacaoBloqueioCartaoRequest request);

}
