package br.com.zup.proposta.responses;

import br.com.zup.proposta.models.*;
import br.com.zup.proposta.models.enums.*;
import com.fasterxml.jackson.annotation.*;

import java.time.*;
import java.util.*;

public class CarteiraCartaoResponse {

    @JsonProperty
    private String email;

    @JsonProperty
    private String emissor;

    public CarteiraCartaoResponse(String id, String email, String emissor) {
        this.email = email;
        this.emissor = emissor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteiraCartaoResponse that = (CarteiraCartaoResponse) o;
        return Objects.equals(email, that.email) && Objects.equals(emissor, that.emissor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, emissor);
    }

    public CarteiraCartao toModel() {
        return new CarteiraCartao(this.email, EmissorCarteira.valueOf(this.emissor));
    }

}
