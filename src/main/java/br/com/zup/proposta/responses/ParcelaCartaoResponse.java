package br.com.zup.proposta.responses;

import br.com.zup.proposta.models.*;
import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class ParcelaCartaoResponse {

    @JsonProperty
    private String id;

    @JsonProperty
    private Integer quantidade;

    @JsonProperty
    private Double valor;

    public ParcelaCartaoResponse(String id, Integer quantidade, Double valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParcelaCartaoResponse that = (ParcelaCartaoResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(quantidade, that.quantidade) && Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidade, valor);
    }

    public ParcelaCartao toModel() {
        return new ParcelaCartao(this.id, this.quantidade, this.valor);
    }

}
