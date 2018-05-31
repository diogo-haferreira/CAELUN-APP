package br.com.caelum.casadocodigo.eventos;

public class MensagemRecebida {
    private String cupom;

    public MensagemRecebida(String cupom) {
        this.cupom = cupom;
    }

    public String getCupom() {
        return cupom;
    }
}
