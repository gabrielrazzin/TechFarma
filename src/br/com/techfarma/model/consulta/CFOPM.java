package br.com.techfarma.model.consulta;

public class CFOPM {
    private String idCFOP;
    private String descricao;
    private String CFOPEquiv;

    public CFOPM(String idCFOP, String descricao, String CFOPEquiv) {
        this.idCFOP = idCFOP;
        this.descricao = descricao;
        this.CFOPEquiv = CFOPEquiv;
    }

    public String getIdCFOP() {
        return idCFOP;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCFOPEquiv() {
        return CFOPEquiv;
    }
}
