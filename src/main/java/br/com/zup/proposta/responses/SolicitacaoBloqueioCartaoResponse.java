package br.com.zup.proposta.responses;

import com.fasterxml.jackson.annotation.*;

public class SolicitacaoBloqueioCartaoResponse {

    @JsonProperty
    private String resultado;

    @Deprecated
    public SolicitacaoBloqueioCartaoResponse() {}

    public SolicitacaoBloqueioCartaoResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

}
