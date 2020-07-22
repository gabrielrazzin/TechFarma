package br.com.techfarma.model.consulta;

public class FormasPagM {
    private int idFormasPag;
    private String descricao;
    private String arredonda;
    private String arredondamento;

    public FormasPagM(int idFormasPag, String descricao, String arredonda, String arredondamento) {
        this.idFormasPag = idFormasPag;
        this.descricao = descricao;
        this.arredonda = arredonda;
        this.arredondamento = arredondamento;
    }

    public int getIdFormasPag() {
        return idFormasPag;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getArredonda() {
        return arredonda;
    }

    public String getArredondamento() {
        return arredondamento;
    }
}
