package br.com.zup.proposta.exceptions;

public class PropostaNaoEncontradaException extends BussinessException {

    public PropostaNaoEncontradaException() {
        super("A proposta informada não foi encontrada!");
    }

}
