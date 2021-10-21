package br.com.zup.proposta.controllers.requests;

import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;

public class PropostaRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Positive
    private BigDecimal salario;

    @NotBlank
    private String endereco;

    @NotBlank
    private String cidade;

    @NotNull
    private Long estadoId;

    public PropostaRequest(String nome, String documento, String email, BigDecimal salario, String endereco, String cidade, Long estadoId) {
        this.nome = nome;
        this.documento = documento;
        this.email = email;
        this.salario = salario;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estadoId = estadoId;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public Proposta toModel(Estado estado) {
        return new Proposta(this.nome, this.documento, this.email, this.salario, this.endereco, this.cidade, estado);
    }

}
