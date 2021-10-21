package br.com.zup.proposta.controllers.exceptions;

public class EstadoJaExistenteException extends BussinessException {

    public EstadoJaExistenteException() {
        super("O estado informado já está cadastrado!");
    }

}
