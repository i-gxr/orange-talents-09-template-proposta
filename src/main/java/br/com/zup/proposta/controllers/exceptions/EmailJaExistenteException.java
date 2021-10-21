package br.com.zup.proposta.controllers.exceptions;

public class EmailJaExistenteException extends BussinessException {

    public EmailJaExistenteException() {
        super("O e-mail informado já está cadastrado!");
    }

}
