package br.com.zup.proposta.services;

import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@FeignClient(name = "consultaFinanceiraService", url = "${proposta.external-api-consulta-url}")
public interface ConsultaFinanceiraService {

    @PostMapping(consumes = "application/json")
    AnaliseResponse postAnaliseRequest(@RequestBody @Valid AnaliseRequest request);

}
