package br.com.zup.proposta.responses;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import java.time.*;
import java.util.*;

public class CarteiraCartaoResponse {

    @JsonProperty
    private String id;

    @JsonProperty
    private String email;

    @JsonProperty
    private LocalDateTime associadoEm;

    @JsonProperty
    private String emissor;

    public CarteiraCartaoResponse(String id, String email, LocalDateTime associadoEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadoEm = associadoEm;
        this.emissor = emissor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteiraCartaoResponse that = (CarteiraCartaoResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(associadoEm, that.associadoEm) && Objects.equals(emissor, that.emissor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, associadoEm, emissor);
    }

    public CarteiraCartao toModel() {
        return new CarteiraCartao(this.id, this.email, this.associadoEm, this.emissor);
    }

}
