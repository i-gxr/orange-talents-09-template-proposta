package br.com.zup.proposta.services;

import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@FeignClient(name = "notificarAvisoViagemService", url = "${proposta.external-api-cartoes-url}")
public interface NotificarAvisoViagemService {

    @PostMapping(value = "/{id}/avisos", consumes = "application/json")
    NotificacaoAvisoCartaoResponse postNotificarAvisoViagemCartaoRequest(@PathVariable String id, @RequestBody @Valid AvisoCartaoRequest request);

}
