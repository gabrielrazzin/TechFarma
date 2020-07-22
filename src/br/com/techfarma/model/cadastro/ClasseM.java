package br.com.techfarma.model.cadastro;

public class ClasseM {
    private int idClasse;
    private String descricao;
    private String tipo;
    private String estqNeg;
    private String controlLote;
    
    public static int idClasseA;

    public ClasseM(String descricao, String tipo, String estqNeg, String controlLote) {
        this.descricao = descricao;
        this.tipo = tipo;
        this.estqNeg = estqNeg;
        this.controlLote = controlLote;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEstqNeg() {
        return estqNeg;
    }

    public String getControlLote() {
        return controlLote;
    }
}
