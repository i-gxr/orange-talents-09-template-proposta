package br.com.zup.proposta.controllers.exceptions;

public class DocumentoJaExistenteException extends BussinessException {

    public DocumentoJaExistenteException() {
        super("O documento informado já está cadastrado!");
    }

}
