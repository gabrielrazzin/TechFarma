package br.com.techfarma.model.cadastro;

public class FabricanteM {

    private int idFabricante;
    private String descricao;
    private String razaoSocial;
    private String CNPJ;
    private String endereco;
    private String cidade;
    private String telefone;
    private String celular;
    private String email;
    private double desconto;
    private String anotacoes;

    public FabricanteM(String descricao, String razaoSocial, String CNPJ, String endereco, String cidade, String telefone, String celular, String email, double desconto, String anotacoes) {
        this.descricao = descricao;
        this.razaoSocial = razaoSocial;
        this.CNPJ = CNPJ;
        this.endereco = endereco;
        this.cidade = cidade;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.desconto = desconto;
        this.anotacoes = anotacoes;
    }

    public FabricanteM(int idFabricante, String descricao, String telefone, double desconto) {
        this.idFabricante = idFabricante;
        this.descricao = descricao;
        this.telefone = telefone;
        this.desconto = desconto;
    }

    public FabricanteM(int idFabricante, String descricao) {
        this.idFabricante = idFabricante;
        this.descricao = descricao;
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public String getdescricao() {
        return descricao;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public String getTelefone() {
        return telefone;
    }
    
    public String getCelular(){
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public double getDesconto() {
        return desconto;
    }

    public String getAnotacoes() {
        return anotacoes;
    }
}
