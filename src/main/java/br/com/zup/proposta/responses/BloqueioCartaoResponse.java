package br.com.zup.proposta.responses;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import java.time.*;
import java.util.*;

public class BloqueioCartaoResponse {

    @JsonProperty
    private String sistemaResponsavel;

    @Deprecated
    public BloqueioCartaoResponse() {}

    public BloqueioCartaoResponse(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public BloqueioCartao toModel() {
        return new BloqueioCartao(this.sistemaResponsavel, null);
    }

}
