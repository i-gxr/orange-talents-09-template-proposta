package br.com.zup.proposta.requests;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.*;
import java.time.*;

public class AvisoCartaoRequest {

    @NotNull
    @FutureOrPresent
    @JsonProperty
    private LocalDate validoAte;

    @NotBlank
    @JsonProperty
    private String destino;

    public AvisoCartaoRequest(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public AvisoCartao toModel(String userAgent, String ipSolicitante) {
        return new AvisoCartao(this.validoAte, this.destino, userAgent, ipSolicitante);
    }

}
