package br.com.zup.proposta.controllers.exceptions;

public class BussinessException extends RuntimeException {

    public BussinessException(String message) {
        super(message);
    }

}
