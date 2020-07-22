package br.com.techfarma.model.cadastro;

public class GrupoM {
    
    private int idGrupo;
    private String descricao, desconto, exgReceita, estqNegativo, coletaCRM;
    private double taxComissao, descAuto, descMax, taxMarcacao, meta;
    
    public GrupoM(String descricao, double taxComissao, double descAuto, double descMax, double taxMarcacao, double meta, String desconto, String exgReceita, String estqNegativo, String coletaCRM) {
        this.descricao = descricao;
        this.taxComissao = taxComissao;
        this.descAuto = descAuto;
        this.descMax = descMax;
        this.taxMarcacao = taxMarcacao;
        this.meta = meta;
        this.desconto = desconto;
        this.exgReceita = exgReceita;
        this.estqNegativo = estqNegativo;
        this.coletaCRM = coletaCRM; 
    }

    public int getIdGrupo() {
        return idGrupo;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public String getDesconto() {
        return desconto;
    }

    public String getExgReceita() {
        return exgReceita;
    }

    public String getEstqNegativo() {
        return estqNegativo;
    }

    public String getColetaCRM() {
        return coletaCRM;
    }

    public double getTaxComissao() {
        return taxComissao;
    }

    public double getDescAuto() {
        return descAuto;
    }

    public double getDescMax() {
        return descMax;
    }

    public double getTaxMarcacao() {
        return taxMarcacao;
    }

    public double getMeta() {
        return meta;
    }
}
