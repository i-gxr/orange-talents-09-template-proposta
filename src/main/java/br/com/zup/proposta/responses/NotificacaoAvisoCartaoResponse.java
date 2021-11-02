package br.com.zup.proposta.responses;

import com.fasterxml.jackson.annotation.*;

public class NotificacaoAvisoCartaoResponse {

    @JsonProperty
    private String resultado;

    @Deprecated
    public NotificacaoAvisoCartaoResponse() {}

    public NotificacaoAvisoCartaoResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

}
