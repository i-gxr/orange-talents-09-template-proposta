package br.com.zup.proposta.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "tb_bloqueio_cartao")
public class BloqueioCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime bloqueadoEm = LocalDateTime.now();

    @NotBlank
    @Column(nullable = false)
    private String sistemaResponsavel;

    @NotBlank
    @Column(nullable = false)
    private String ipSolicitante;

    @Column(nullable = false)
    private boolean ativo = true;

    @Deprecated
    public BloqueioCartao() {}

    public BloqueioCartao(String sistemaResponsavel, String ipSolicitante) {
        this.sistemaResponsavel = sistemaResponsavel;
        this.ipSolicitante = ipSolicitante;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloqueioCartao that = (BloqueioCartao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
