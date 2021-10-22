package br.com.zup.proposta.exceptions;

public class PaisNaoEncontradoException extends BussinessException {

    public PaisNaoEncontradoException() {
        super("O país informado não foi encontrado!");
    }

}
