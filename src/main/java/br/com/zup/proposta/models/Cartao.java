package br.com.zup.proposta.models;

import br.com.zup.proposta.exceptions.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "tb_cartao")
public class Cartao {

    @Id
    private String numeroCartao;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime emitidoEm;

    @NotBlank
    @Column(nullable = false)
    private String titular;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "numero_cartao")
    private Set<BloqueioCartao> bloqueios;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "numero_cartao")
    private Set<AvisoCartao> avisos;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "numero_cartao")
    private Set<CarteiraCartao> carteiras;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "numero_cartao")
    private Set<ParcelaCartao> parcelas;

    @NotNull
    @Column(nullable = false)
    private Double limite;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private RenegociacaoCartao renegociacao;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private VencimentoCartao vencimento;

    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;

    @Deprecated
    public Cartao() {}

    public Cartao(String numeroCartao, LocalDateTime emitidoEm, String titular, Set<BloqueioCartao> bloqueios, Set<AvisoCartao> avisos, Set<CarteiraCartao> carteiras, Set<ParcelaCartao> parcelas, Double limite, RenegociacaoCartao renegociacao, VencimentoCartao vencimento, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.proposta = proposta;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public boolean isBloqueado() {
        return this.bloqueios.stream().anyMatch(BloqueioCartao::isAtivo);
    }

    public void addBloqueios(BloqueioCartao bloqueioCartao) {
        if (isBloqueado())
            throw new CartaoBloqueadoException();
        this.bloqueios.add(bloqueioCartao);
    }

}
