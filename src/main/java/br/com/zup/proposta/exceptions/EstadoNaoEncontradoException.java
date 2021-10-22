package br.com.zup.proposta.exceptions;

public class EstadoNaoEncontradoException extends BussinessException {

    public EstadoNaoEncontradoException() {
        super("O estado informado não foi encontrado!");
    }

}
