package br.com.techfarma.model.consulta;

public class CartaoM {
    private int idCartao;
    private String descricao;
    private String tipoCartao;
    private String bandeira;

    public CartaoM(int idCartao, String descricao, String tipoCartao, String bandeira) {
        this.idCartao = idCartao;
        this.descricao = descricao;
        this.tipoCartao = tipoCartao;
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

    public String getBandeira() {
        return bandeira;
    }
}
