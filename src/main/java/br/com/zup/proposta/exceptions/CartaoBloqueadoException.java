package br.com.zup.proposta.exceptions;

public class CartaoBloqueadoException extends BussinessException {

    public CartaoBloqueadoException() {
        super("O cartão já está bloqueado!");
    }

}
