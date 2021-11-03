package br.com.zup.proposta.requests;

import br.com.zup.proposta.models.*;
import br.com.zup.proposta.models.enums.*;

import javax.persistence.*;
import javax.validation.constraints.*;

public class CarteiraCartaoRequest {

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EmissorCarteira carteira;

    public CarteiraCartaoRequest(String email, EmissorCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public CarteiraCartao toModel() {
        return new CarteiraCartao(this.email, this.carteira);
    }

    public AssociarCarteiraCartaoRequest toAssociarCarteiraCartaoRequest() {
        return new AssociarCarteiraCartaoRequest(this.email, this.carteira.toString());
    }

}
