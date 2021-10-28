package br.com.zup.proposta.exceptions;

public class CartaoNaoEncontradoException extends BussinessException {

    public CartaoNaoEncontradoException() {
        super("O cartão informado não foi encontrado!");
    }

}
