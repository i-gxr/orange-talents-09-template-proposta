package br.com.zup.proposta.services;

import br.com.zup.proposta.responses.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "associacaoCartaoService", url = "${proposta.external-api-cartoes-url}")
public interface AssociacaoCartaoService {

    @GetMapping(consumes = "application/json")
    AssociacaoCartaoResponse getAssociacaoCartaoRequest(@RequestParam String idProposta);

}
