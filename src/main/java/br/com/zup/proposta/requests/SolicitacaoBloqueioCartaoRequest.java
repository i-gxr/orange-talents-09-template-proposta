package br.com.zup.proposta.requests;

import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.*;

public class SolicitacaoBloqueioCartaoRequest {

    @NotBlank
    @JsonProperty
    private String sistemaResponsavel;

    @Deprecated
    public SolicitacaoBloqueioCartaoRequest() {}

    public SolicitacaoBloqueioCartaoRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

}
