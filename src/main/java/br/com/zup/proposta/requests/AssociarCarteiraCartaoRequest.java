package br.com.zup.proposta.requests;

import br.com.zup.proposta.models.*;
import br.com.zup.proposta.models.enums.*;

import javax.persistence.*;
import javax.validation.constraints.*;

public class AssociarCarteiraCartaoRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String carteira;

    public AssociarCarteiraCartaoRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }

}
