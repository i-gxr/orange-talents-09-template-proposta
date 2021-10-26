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

    public void connectProposta(AssociacaoCartaoRequest associacaoCartaoRequest) {
        AssociacaoCartaoResponse response = associacaoCartaoService.postAssociacaoCartaoRequest(associacaoCartaoRequest);
        Proposta proposta = propostaRepository.findById(Long.parseLong(response.getIdProposta())).orElseThrow(PropostaNaoEncontradaException::new);
        Cartao cartao = response.toModel(proposta);
        proposta.connectCartao(cartao);
        propostaRepository.save(proposta);
    }

    @Scheduled(fixedRate = 15000)
    public void connectPropostasWithoutCartao() {
        List<Proposta> propostas = propostaRepository.findAllByCartaoNumeroCartaoIsNullAndStatusProposta(StatusProposta.ELEGIVEL);
        propostas.forEach(p -> connectProposta(new AssociacaoCartaoRequest(p.getDocumento(), p.getNome(), p.getId().toString())));
    }

}
