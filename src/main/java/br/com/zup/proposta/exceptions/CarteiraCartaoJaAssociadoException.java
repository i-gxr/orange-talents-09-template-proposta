package br.com.zup.proposta.exceptions;

public class CarteiraCartaoJaAssociadoException extends BussinessException {

    public CarteiraCartaoJaAssociadoException() {
        super("A carteira informada já está associada a esse cartão!");
    }

}
