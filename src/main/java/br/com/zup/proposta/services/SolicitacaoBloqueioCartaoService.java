package br.com.zup.proposta.services;

import br.com.zup.proposta.responses.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;

@FeignClient(name = "solicitacaoBloqueioCartaoService", url = "${proposta.external-api-cartoes-url}/{id}/bloqueios")
public interface SolicitacaoBloqueioCartaoService {

    @PostMapping(consumes = "application/json")
    SolicitacaoBloqueioCartaoResponse postSolicitacaoBloqueioCartaoRequest(@PathVariable String id, @RequestBody @NotBlank String sistemaResponsavel);

}
