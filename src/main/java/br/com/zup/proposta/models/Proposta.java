package br.com.zup.proposta.models;

import br.com.zup.proposta.responses.*;
import br.com.zup.proposta.models.enums.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;

@Entity(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String documento;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String endereco;

    @NotBlank
    @Column(nullable = false)
    private String cidade;

    @ManyToOne
    private Estado estado;

    @Deprecated
    public Proposta() {}

    public Proposta(String nome, String documento, String email, BigDecimal salario, String endereco, String cidade, Estado estado) {
        this.nome = nome;
        this.documento = documento;
        this.email = email;
        this.salario = salario;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setStatusProposta(AnaliseResponse analiseResponse) {
        if (analiseResponse.getResultadoSolicitacao().equalsIgnoreCase("SEM_RESTRICAO") &&
            analiseResponse.getDocumento().equalsIgnoreCase(this.documento) &&
            analiseResponse.getIdProposta().equalsIgnoreCase(this.id.toString()))
            this.statusProposta = StatusProposta.ELEGIVEL;
        else
            this.statusProposta = StatusProposta.NAO_ELEGIVEL;
    }

}
