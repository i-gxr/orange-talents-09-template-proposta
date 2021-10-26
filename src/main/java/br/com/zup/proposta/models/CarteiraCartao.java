package br.com.zup.proposta.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "tb_carteira_cartao")
public class CarteiraCartao {

    @Id
    private String id;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime associadoEm;

    @NotBlank
    @Column(nullable = false)
    private String emissor;

    @Deprecated
    public CarteiraCartao() {}

    public CarteiraCartao(String id, String email, LocalDateTime associadoEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadoEm = associadoEm;
        this.emissor = emissor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteiraCartao that = (CarteiraCartao) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(associadoEm, that.associadoEm) && Objects.equals(emissor, that.emissor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, associadoEm, emissor);
    }

}
