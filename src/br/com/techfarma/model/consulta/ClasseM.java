package br.com.techfarma.model.consulta;

public class ClasseM {
    private int idClasse;
    private String descricao;
    private String tipo;
         
    private int idProduto;
    private String produtoMC;
    private String classeMC;
    private String grupoMC; 
    
    public static int idClasseS;
    public static String descricaoS;
    

    public ClasseM(int idClasse, String descricao) {
        this.idClasse = idClasse;
        this.descricao = descricao;
    }

    public ClasseM(int idClasse, String descricao, String tipo) {
        this.idClasse = idClasse;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public ClasseM(int idProduto, String produtoMC, String classeMC, String grupoMC) {
        this.idProduto = idProduto;
        this.produtoMC = produtoMC;
        this.classeMC = classeMC;
        this.grupoMC = grupoMC;
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

    @Override
    public String toString(){
        return descricao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getProdutoMC() {
        return produtoMC;
    }

    public String getClasseMC() {
        return classeMC;
    }

    public String getGrupoMC() {
        return grupoMC;
    }
}
