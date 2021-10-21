package br.com.zup.proposta.controllers.exceptions;

public class PaisNaoEncontradoException extends BussinessException {

    public PaisNaoEncontradoException() {
        super("O país informado não foi encontrado!");
    }

}
