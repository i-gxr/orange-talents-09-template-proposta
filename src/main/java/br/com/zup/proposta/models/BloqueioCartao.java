package br.com.zup.proposta.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "tb_bloqueio_cartao")
public class BloqueioCartao {

    @Id
    private String id;

    @NotBlank
    @Column(nullable = false)
    private LocalDateTime bloqueadoEm;

    @NotBlank
    @Column(nullable = false)
    private String sistemaResponsavel;

    @NotNull
    @Column(nullable = false)
    private boolean ativo;

    @Deprecated
    public BloqueioCartao() {}

    public BloqueioCartao(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloqueioCartao that = (BloqueioCartao) o;
        return ativo == that.ativo && Objects.equals(id, that.id) && Objects.equals(bloqueadoEm, that.bloqueadoEm) && Objects.equals(sistemaResponsavel, that.sistemaResponsavel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bloqueadoEm, sistemaResponsavel, ativo);
    }

}
