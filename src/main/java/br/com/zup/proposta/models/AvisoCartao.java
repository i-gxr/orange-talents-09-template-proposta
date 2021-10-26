package br.com.zup.proposta.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "tb_aviso_cartao")
public class AvisoCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime validoAte;

    @NotBlank
    @Column(nullable = false)
    private String destino;

    @Deprecated
    public AvisoCartao() {}

    public AvisoCartao(LocalDateTime validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisoCartao that = (AvisoCartao) o;
        return Objects.equals(id, that.id) && Objects.equals(validoAte, that.validoAte) && Objects.equals(destino, that.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, validoAte, destino);
    }

}
