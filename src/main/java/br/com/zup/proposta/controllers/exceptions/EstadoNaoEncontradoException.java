package br.com.zup.proposta.controllers.exceptions;

public class EstadoNaoEncontradoException extends BussinessException {

    public EstadoNaoEncontradoException() {
        super("O estado informado não foi encontrado!");
    }

}
