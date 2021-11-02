package br.com.zup.proposta.exceptions;

public class AvisoCartaoJaInformadoException extends BussinessException {

    public AvisoCartaoJaInformadoException() {
        super("O aviso de viagem do cartão informado já foi cadastrado!");
    }

}
