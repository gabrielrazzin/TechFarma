package br.com.techfarma.model.cadastro;

public class CFOPM {
    private int idCFOP, CFOPEquiv;
    private String descricao, observacao;

    public CFOPM(int idCFOP, String descricao, String observacao, int CFOPEquiv) {
        this.idCFOP = idCFOP;
        this.descricao = descricao;
        this.observacao = observacao;
        this.CFOPEquiv = CFOPEquiv;
    }

    public int getIdCFOP() {
        return idCFOP;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public int getCFOPEquiv() {
        return CFOPEquiv;
    }
}