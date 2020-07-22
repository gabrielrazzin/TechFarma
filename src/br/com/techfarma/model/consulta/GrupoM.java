package br.com.techfarma.model.consulta;

import javafx.scene.control.CheckBox;

public class GrupoM {
    private int idGrupo;
    private String descricao;
    private double taxComissao;
    private double descMax;
    private int qtdProdutos;
    private int undEstoque;
    private double totalCusto;
    private double totalVenda;
    
    private int idProduto;
    private String grupoDescricao;
    private String classeDescricao;
    
    public static int idGrupoS;
    public static String descricaoS;

    public GrupoM(int idGrupo, String descricao) {
        this.idGrupo = idGrupo;
        this.descricao = descricao;
    }
    
    public GrupoM(int idGrupo, String descricao, double taxComissao, double descMax) {
        this.idGrupo = idGrupo;
        this.descricao = descricao;
        this.taxComissao = taxComissao;
        this.descMax = descMax;
    }

    public GrupoM(int idProduto, String descricao, String classeDescricao, String grupoDescricao) {
        this.idProduto = idProduto;
        this.descricao = descricao;      
        this.classeDescricao = classeDescricao;
        this.grupoDescricao = grupoDescricao;
    }
    
    public int getIdGrupo() {
        return idGrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getTaxComissao() {
        return taxComissao;
    }

    public double getDescMax() {
        return descMax;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getClasseDescricao() {
        return classeDescricao;
    }

    public String getGrupoDescricao() {
        return grupoDescricao;
    }
    
    /*/@Override
    public String toString(){
        return grupoDescricao;
    }/*/
}
