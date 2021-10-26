package br.com.zup.proposta.responses;

import br.com.zup.proposta.exceptions.*;
import br.com.zup.proposta.models.*;
import br.com.zup.proposta.repositories.*;
import com.fasterxml.jackson.annotation.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

public class AssociacaoCartaoResponse {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Set<BloqueioCartaoResponse> bloqueios;
    private Set<AvisoCartaoResponse> avisos;
    private Set<CarteiraCartaoResponse> carteiras;
    private Set<ParcelaCartaoResponse> parcelas;
    private Double limite;
    private RenegociacaoCartaoResponse renegociacao;
    private VencimentoCartaoResponse vencimento;
    private String idProposta;

    public AssociacaoCartaoResponse(String id, LocalDateTime emitidoEm, String titular, Set<BloqueioCartaoResponse> bloqueios, Set<AvisoCartaoResponse> avisos, Set<CarteiraCartaoResponse> carteiras, Set<ParcelaCartaoResponse> parcelas, Double limite, RenegociacaoCartaoResponse renegociacao, VencimentoCartaoResponse vencimento, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public Cartao toModel(Proposta proposta) {
        return new Cartao(
                this.id,
                this.emitidoEm,
                this.titular,
                this.bloqueios.stream().map(BloqueioCartaoResponse::toModel).collect(Collectors.toSet()),
                this.avisos.stream().map(AvisoCartaoResponse::toModel).collect(Collectors.toSet()),
                this.carteiras.stream().map(CarteiraCartaoResponse::toModel).collect(Collectors.toSet()),
                this.parcelas.stream().map(ParcelaCartaoResponse::toModel).collect(Collectors.toSet()),
                this.limite,
                this.renegociacao != null ? this.renegociacao.toModel() : null,
                this.vencimento != null ? this.vencimento.toModel() : null,
                proposta
        );
    }
}
