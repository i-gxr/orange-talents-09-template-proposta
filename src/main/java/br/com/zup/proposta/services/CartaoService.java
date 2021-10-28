package br.com.zup.proposta.services;

import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.models.enums.*;
import br.com.zup.proposta.repositories.*;
import br.com.zup.proposta.requests.*;
import br.com.zup.proposta.responses.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CartaoService {

    @Autowired
    private AssociacaoCartaoService associacaoCartaoService;

    @Autowired
    private PropostaRepository propostaRepository;

    public void connectProposta(Proposta proposta) {
        AssociacaoCartaoResponse response = associacaoCartaoService.getAssociacaoCartaoRequest(proposta.getId().toString());
        Cartao cartao = response.toModel(proposta);
        proposta.connectCartao(cartao);
        propostaRepository.save(proposta);
    }

    @Scheduled(fixedRateString = "${proposta.external-api.period-perform-task}")
    public void connectPropostasWithoutCartao() {
        List<Proposta> propostas = propostaRepository.findAllByCartaoNumeroCartaoIsNullAndStatusProposta(StatusProposta.ELEGIVEL);
        propostas.forEach(this::connectProposta);
    }

}
