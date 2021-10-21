package br.com.zup.proposta.controllers.requests;

import br.com.zup.proposta.models.*;

import javax.validation.constraints.*;

public class EstadoRequest {

    @NotBlank
    private String nome;

    @NotNull
    private Long paisId;

    public EstadoRequest(String nome, Long paisId) {
        this.nome = nome;
        this.paisId = paisId;
    }

    public String getNome() {
        return nome;
    }

    public Long getPaisId() {
        return paisId;
    }

    public Estado toModel(Pais pais) {
        return new Estado(this.nome, pais);
    }

}
