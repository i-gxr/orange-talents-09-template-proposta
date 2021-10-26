package br.com.zup.proposta.responses;

import com.fasterxml.jackson.annotation.*;

import java.math.*;

public class PropostaResponse {

    @JsonProperty
    private String nome;

    @JsonProperty
    private String documento;

    @JsonProperty
    private String email;

    @JsonProperty
    private BigDecimal salario;

    @JsonProperty
    private String statusProposta;

    @JsonProperty
    private String endereco;

    @JsonProperty
    private String cidade;

    @JsonProperty
    private String estado;

    @JsonProperty
    private String pais;

    @JsonProperty
    private String cartao;

    public PropostaResponse(String nome, String documento, String email, BigDecimal salario, String statusProposta, String endereco, String cidade, String estado, String pais, String cartao) {
        this.nome = nome;
        this.documento = documento;
        this.email = email;
        this.salario = salario;
        this.statusProposta = statusProposta;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cartao = cartao != null ? cartao : "";
    }

}
