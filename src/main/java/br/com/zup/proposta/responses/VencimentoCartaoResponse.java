package br.com.zup.proposta.responses;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import java.time.*;

public class VencimentoCartaoResponse {

    @JsonProperty
    private String id;

    @JsonProperty
    private Integer dia;

    @JsonProperty
    private LocalDateTime dataDeCriacao;

    public VencimentoCartaoResponse(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public VencimentoCartao toModel() {
        return new VencimentoCartao(this.id, this.dia, this.dataDeCriacao);
    }

}
