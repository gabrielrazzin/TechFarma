package br.com.techfarma.model.cadastro;

public class MarkUpM {
    private int idLista;
    private String descricao;
    private double markUp;

    public MarkUpM() {
    }
    
    public MarkUpM(String descricao, double markUp) {
        this.descricao = descricao;
        this.markUp = markUp;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getMarkUp() {
        return markUp;
    }

    public void setMarkUp(double markUp) {
        this.markUp = markUp;
    }
}
