package br.com.techfarma.model.consulta;

public class GrupoFilialM {
    private int idGrupoFilial;
    private String descricao;

    public GrupoFilialM(int idGrupoFilial, String descricao) {
        this.idGrupoFilial = idGrupoFilial;
        this.descricao = descricao;
    }

    public int getIdGrupoFilial() {
        return idGrupoFilial;
    }

    public String getDescricao() {
        return descricao;
    }
}
