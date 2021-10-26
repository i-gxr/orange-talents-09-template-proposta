package br.com.zup.proposta.responses;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import java.time.*;

public class RenegociacaoCartaoResponse {

    @JsonProperty
    private String id;

    @JsonProperty
    private Integer quantidade;

    @JsonProperty
    private Double valor;

    @JsonProperty
    private LocalDateTime dataDeCriacao;

    public RenegociacaoCartaoResponse(String id, Integer quantidade, Double valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public RenegociacaoCartao toModel() {
        return new RenegociacaoCartao(this.id, this.quantidade, this.valor, this.dataDeCriacao);
    }

}
