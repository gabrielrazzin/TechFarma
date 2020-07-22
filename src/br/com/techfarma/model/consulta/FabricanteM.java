package br.com.techfarma.model.consulta;

public class FabricanteM {
    private int idFabricante;
    private String descricao;
    private String telefone;
    private double desconto;
    
    public static int idFabricanteS;
    public static String nomeS;
    
    private int idProdutoMF;
    private String descricaoMF;
    private String fabricanteMF;
    
    private int idProdutoMG;
    private String descricaoMG;
    private String grupoMG;
    private String fabricanteMG;

    public FabricanteM(int idFabricante, String descricao, String telefone, double desconto) {
        this.idFabricante = idFabricante;
        this.descricao = descricao;
        this.telefone = telefone;
        this.desconto = desconto;
    }

    public FabricanteM(int idProdutoMF, String descricaoMF, String fabricanteMF) {
        this.idProdutoMF = idProdutoMF;
        this.descricaoMF = descricaoMF;
        this.fabricanteMF = fabricanteMF;
    }

    public FabricanteM(int idProdutoMG, String descricaoMG, String grupoMG, String fabricanteMG) {
        this.idProdutoMG = idProdutoMG;
        this.descricaoMG = descricaoMG;
        this.grupoMG = grupoMG;
        this.fabricanteMG = fabricanteMG;
    }

    public FabricanteM(int idFabricante, String descricao) {
        this.idFabricante = idFabricante;
        this.descricao = descricao;
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public double getDesconto() {
        return desconto;
    }

    public int getIdProdutoMF() {
        return idProdutoMF;
    }

    public String getDescricaoMF() {
        return descricaoMF;
    }

    public String getFabricanteMF() {
        return fabricanteMF;
    }

    public int getIdProdutoMG() {
        return idProdutoMG;
    }

    public String getDescricaoMG() {
        return descricaoMG;
    }

    public String getGrupoMG() {
        return grupoMG;
    }

    public String getFabricanteMG() {
        return fabricanteMG;
    }
}
