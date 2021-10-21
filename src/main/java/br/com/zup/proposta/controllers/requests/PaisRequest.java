package br.com.zup.proposta.controllers.requests;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.*;

public class PaisRequest {

    @JsonProperty
    @NotNull
    private String nome;

    @Deprecated
    public PaisRequest() {}

    public PaisRequest(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Pais toModel() {
        return new Pais(this.nome);
    }

}
