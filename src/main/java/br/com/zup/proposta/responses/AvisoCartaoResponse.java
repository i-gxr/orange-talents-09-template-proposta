package br.com.zup.proposta.responses;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import java.time.*;
import java.util.*;

public class AvisoCartaoResponse {

    @JsonProperty
    private LocalDateTime validoAte;

    @JsonProperty
    private String destino;

    public AvisoCartaoResponse(LocalDateTime validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisoCartaoResponse that = (AvisoCartaoResponse) o;
        return Objects.equals(validoAte, that.validoAte) && Objects.equals(destino, that.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validoAte, destino);
    }

    public AvisoCartao toModel() {
        return new AvisoCartao(this.validoAte, this.destino);
    }

}
