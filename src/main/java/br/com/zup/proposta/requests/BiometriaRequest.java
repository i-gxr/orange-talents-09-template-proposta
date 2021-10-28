package br.com.zup.proposta.requests;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.*;

public class BiometriaRequest {

    @NotBlank
    private String fingerprint;

    @Deprecated
    public BiometriaRequest() {}

    public BiometriaRequest(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(this.fingerprint, cartao);
    }

}
