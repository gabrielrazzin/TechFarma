package br.com.techfarma.model.cadastro;

public class GrupoCompraM {
    private int idGrupoC;
    private String descricao;

    public GrupoCompraM(String descricao) {
        this.descricao = descricao;
    }

    public int getIdGrupoC() {
        return idGrupoC;
    }

    public String getDescricao() {
        return descricao;
    }
}
