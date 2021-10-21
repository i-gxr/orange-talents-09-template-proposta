package br.com.zup.proposta.controllers.exceptions;

public class PaisJaExistenteException extends BussinessException {

    public PaisJaExistenteException() {
        super("O país informado já está cadastrado!");
    }

}
