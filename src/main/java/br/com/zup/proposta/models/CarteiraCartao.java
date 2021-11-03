package br.com.zup.proposta.models;

import br.com.zup.proposta.models.enums.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "tb_carteira_cartao")
public class CarteiraCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime associadoEm = LocalDateTime.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmissorCarteira emissor;

    @Deprecated
    public CarteiraCartao() {}

    public CarteiraCartao(String email, EmissorCarteira emissor) {
        this.email = email;
        this.emissor = emissor;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteiraCartao that = (CarteiraCartao) o;
        return emissor == that.emissor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(emissor);
    }

}
