package br.com.zup.proposta.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@Entity
@Table(name = "tb_biometria_cartao")
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotBlank
    @Column(nullable = false)
    private String fingerprint;

    @Column(nullable = false)
    private LocalDateTime dataHoraAssociacao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "numero_cartao")
    private Cartao cartao;

    @Deprecated
    public Biometria() {}

    public Biometria(String fingerprint, Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

}
