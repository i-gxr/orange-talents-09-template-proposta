package br.com.zup.proposta.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@Entity
@Table(name = "tb_vencimento_cartao")
public class VencimentoCartao {

    @Id
    private String id;

    @NotNull
    @Column(nullable = false)
    private Integer dia;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public VencimentoCartao() {}

    public VencimentoCartao(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

}
