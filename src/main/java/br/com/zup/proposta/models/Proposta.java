package br.com.zup.proposta.models;

import br.com.zup.proposta.commons.utils.*;
import br.com.zup.proposta.models.enums.*;
import br.com.zup.proposta.responses.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    @Convert(converter = EncryptConverter.class)
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
    @Column(nullable = false)
    private String endereco;

    @NotBlank
    @Column(nullable = false)
    private String cidade;

    @ManyToOne
    private Estado estado;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "numero_cartao")
    private Cartao cartao;

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

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatusProposta(String resultadoSolicitacao) {
        if (resultadoSolicitacao.equalsIgnoreCase("SEM_RESTRICAO"))
            this.statusProposta = StatusProposta.ELEGIVEL;
        else
            this.statusProposta = StatusProposta.NAO_ELEGIVEL;
    }

    public void connectCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public PropostaResponse toResponse() {
        return new PropostaResponse(
                this.nome,
                this.documento,
                this.email,
                this.salario,
                this.statusProposta.toString(),
                this.endereco,
                this.cidade,
                this.estado.getNome(),
                this.estado.getPais().getNome(),
                this.cartao != null ? this.cartao.getNumeroCartao() : null
        );
    }

}
