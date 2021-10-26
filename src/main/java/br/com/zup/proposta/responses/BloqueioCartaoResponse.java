package br.com.zup.proposta.responses;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import java.time.*;
import java.util.*;

public class BloqueioCartaoResponse {

    @JsonProperty
    private String id;

    @JsonProperty
    private LocalDateTime bloqueadoEm;

    @JsonProperty
    private String sistemaResponsavel;

    @JsonProperty
    private boolean ativo;

    public BloqueioCartaoResponse(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloqueioCartaoResponse that = (BloqueioCartaoResponse) o;
        return ativo == that.ativo && Objects.equals(id, that.id) && Objects.equals(bloqueadoEm, that.bloqueadoEm) && Objects.equals(sistemaResponsavel, that.sistemaResponsavel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bloqueadoEm, sistemaResponsavel, ativo);
    }

    public BloqueioCartao toModel() {
        return new BloqueioCartao(this.id, this.bloqueadoEm, this.sistemaResponsavel, this.ativo);
    }

}
