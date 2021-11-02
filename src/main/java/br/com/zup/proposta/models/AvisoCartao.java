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
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate validoAte;

    @NotBlank
    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDateTime notificadoEm = LocalDateTime.now();

    @NotBlank
    @Column(nullable = false)
    private String userAgent;

    @NotBlank
    @Column(nullable = false)
    private String ipSolicitante;

    @Deprecated
    public AvisoCartao() {}

    public AvisoCartao(LocalDate validoAte, String destino, String userAgent, String ipSolicitante) {
        this.validoAte = validoAte;
        this.destino = destino;
        this.userAgent = userAgent;
        this.ipSolicitante = ipSolicitante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisoCartao that = (AvisoCartao) o;
        return Objects.equals(validoAte, that.validoAte) && Objects.equals(destino, that.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validoAte, destino);
    }

}
