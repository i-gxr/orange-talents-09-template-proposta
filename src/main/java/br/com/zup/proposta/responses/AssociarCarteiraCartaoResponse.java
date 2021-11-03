package br.com.zup.proposta.responses;

public class AssociarCarteiraCartaoResponse {

    private String resultado;
    private String id;

    public AssociarCarteiraCartaoResponse(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

}
