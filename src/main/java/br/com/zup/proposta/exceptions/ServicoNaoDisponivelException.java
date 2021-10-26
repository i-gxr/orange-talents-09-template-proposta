package br.com.zup.proposta.exceptions;

public class ServicoNaoDisponivelException extends BussinessException {

    public ServicoNaoDisponivelException() {
        super("O serviço solicitado não está disponível");
    }

}
