package br.com.techfarma.model.cadastro;

public class CartoesM {
    private int idCartao, prazoPag;
    private String descricao,tipoCartao, usaTEF, bandeira;
    private double taxADM, taxADMParc;

    public CartoesM(String descricao, String tipoCartao, double taxADM, double taxADMParc, int prazoPag, String usaTEF, String bandeira) {
        this.descricao = descricao;
        this.tipoCartao = tipoCartao;
        this.taxADM = taxADM;
        this.taxADMParc = taxADMParc;
        this.prazoPag = prazoPag;
        this.usaTEF = usaTEF;
        this.bandeira = bandeira;
    }

    public int getIdCartao() {
        return idCartao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipoCartao() {
        return tipoCartao;
    }

    public double getTaxADM() {
        return taxADM;
    }

    public double getTaxADMParc() {
        return taxADMParc;
    }

    public int getPrazoPag() {
        return prazoPag;
    }

    public String getUsaTEF() {
        return usaTEF;
    }

    public String getBandeira() {
        return bandeira;
    }
}
