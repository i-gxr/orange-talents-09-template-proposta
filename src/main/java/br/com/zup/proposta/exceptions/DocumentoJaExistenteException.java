package br.com.zup.proposta.exceptions;

public class DocumentoJaExistenteException extends BussinessException {

    public DocumentoJaExistenteException() {
        super("O documento informado já está em uso!");
    }

}
